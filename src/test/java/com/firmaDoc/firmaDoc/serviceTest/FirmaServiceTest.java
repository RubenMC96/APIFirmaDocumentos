package com.firmaDoc.firmaDoc.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.firmaDoc.domain.DTO.DocumentoDTO;
import com.firmaDoc.domain.DTO.VerificacionDTO;
import com.firmaDoc.service.FirmaServiceImp;


@ExtendWith(MockitoExtension.class)
public class FirmaServiceTest {
    
    @InjectMocks
    private FirmaServiceImp firmaService;
    private KeyPair keyPair;

    @BeforeEach
    void initKeyPair() throws Exception {
        // 3) Ahora sí inyectamos por reflexión el KeyPair en la instancia que Mockito creó
        keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

    }

    /**
     * Test para verificar que el servicio de firma funciona correctamente.
     * Se crea un documento, se genera una firma y se verifica que la firma generada es correcta.
     */
    @Test
    public void testFirmarDocumento_Correcto() throws Exception {
        
        DocumentoDTO documento = new DocumentoDTO();
        documento.setDocumento("Documento a firmar");

        String clavePrivada = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        String firmaGenerada = firmaService.firmarDocumento(clavePrivada, documento);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(documento.getDocumento().getBytes(StandardCharsets.UTF_8));
        byte[] firma = signature.sign();

        String firmaEsperada = Base64.getEncoder().encodeToString(firma);


        assertEquals(firmaEsperada, firmaGenerada);
    }

    /**
     * Test para verificar que el servicio de firma lanza una excepción cuando se intenta firmar un documento nulo.
     * Se espera que se lance una excepción al intentar firmar un documento nulo.
     */

    @Test
    public void testFirmarDocumento_Error() throws Exception {
        
        DocumentoDTO documento = new DocumentoDTO();
        documento.setDocumento(null);

        assertThrows(Exception.class, () -> {
        firmaService.firmarDocumento("clavePrivada", documento);
        });

    }

    /**
     * Test para verificar que el servicio de verificación de firma funciona correctamente.
     * Se crea un documento, se genera una firma y se verifica que la firma es correcta.
     */

/*  @Test
    public void testVerificarFirma_Correcto() throws Exception {
        
        VerificacionDTO documento = new VerificacionDTO();
        documento.setDocumento("Documento a verificar");

        String clavePrivada = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        String firmaGenerada = firmaService.firmarDocumento(clavePrivada, new DocumentoDTO(documento.getDocumento()));
        documento.setFirma(firmaGenerada);

        boolean resultado = firmaService.verificarFirma(documento, "clavePrivada");
        assertEquals(true, resultado);
    
    }*/

    @Test
    public void  testVerificarFirma_Correcto() throws Exception{

        DocumentoDTO documento = new DocumentoDTO();
        documento.setDocumento("Documento a firmar");

        String clavePrivada = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String clavePublica = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        String firmaGenerada = firmaService.firmarDocumento(clavePrivada, documento);

        VerificacionDTO verificacionDTO = new VerificacionDTO();
        verificacionDTO.setDocumento(documento.getDocumento());
        verificacionDTO.setFirma(firmaGenerada);
        verificacionDTO.setToken("token");

        boolean resultado = firmaService.verificarFirma(verificacionDTO, clavePublica);
        assertEquals(true, resultado);
    }

    /**
     * Test para verificar que el servicio de verificación de firma lanza una excepción cuando se intenta verificar con una firma incorrecta.
     * Se espera que se lance una excepción al intentar verificar un documento con una clave incorrecta.
     */

    @Test
    public void testVerificarFirma_Error() throws Exception {
        
        DocumentoDTO documento = new DocumentoDTO();
        documento.setDocumento("Documento a firmar");

        String clavePrivada = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String clavePublica = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        String modificada = "A" + clavePublica.substring(1);


        String firmaGenerada = firmaService.firmarDocumento(clavePrivada, documento);

        VerificacionDTO verificacionDTO = new VerificacionDTO();
        verificacionDTO.setDocumento(documento.getDocumento());
        verificacionDTO.setFirma(firmaGenerada);
        verificacionDTO.setToken("token");



        //boolean resultado = firmaService.verificarFirma(verificacionDTO, modificada);
        assertThrows(RuntimeException.class, () -> {
                firmaService.verificarFirma(verificacionDTO, modificada);
            });    
    }



}
