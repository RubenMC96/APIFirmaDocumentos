package com.firmaDoc.firmaDoc.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.firmaDoc.Utils.JwtUtil;
import com.firmaDoc.domain.DTO.DocumentoDTO;
import com.firmaDoc.domain.DTO.FirmaDTO;
import com.firmaDoc.domain.DTO.FirmarDTO;
import com.firmaDoc.domain.DTO.VerificacionDTO;
import com.firmaDoc.domain.entity.Usuario;
import com.firmaDoc.repository.UsuarioRepository;
import com.firmaDoc.restController.signatureRestController;
import com.firmaDoc.service.FirmaService;
import com.firmaDoc.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class SignatureRestControllerTest {

  @Mock
  private FirmaService firmaService;
  @Mock
  private UsuarioService usuarioService;
  @Mock
  private UsuarioRepository usuarioRepository;

  @InjectMocks
  private signatureRestController signatureRestController; 

    @BeforeEach
    void initKeyPair() throws Exception {
        
    }

    @Test
    void testFirmarDocumento_Correcto() throws Exception {
        String documentoTxt   = "Documento a firmar";
        String token          = "token";
        String clavePrivada   = "clavePrivadaY";
        String expectedFirma  = "firmaGenerada";

        FirmarDTO input = new FirmarDTO(documentoTxt, token);

        Usuario usuario = new Usuario();
        usuario.setClavePrivada(clavePrivada);
        usuario.setToken(token);
        when(usuarioService.obtenerUsuarioByToken(eq(token)))
            .thenReturn(usuario);

        when(firmaService.firmarDocumento(
                eq(clavePrivada), any(DocumentoDTO.class)))
            .thenReturn(expectedFirma);

        try (MockedStatic<JwtUtil> jwtMock = Mockito.mockStatic(JwtUtil.class)) {
            jwtMock
                .when(() -> JwtUtil.validateToken(token))
                .thenReturn(true);

        ResponseEntity<FirmaDTO> response =
            signatureRestController.firmarDocumento(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        FirmaDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(expectedFirma, body.getFirma());
        }
    }


@Test
void testFirmarDocumento_Error() throws Exception {
    // Arrange
    String documentoTxt = "Documento a firmar";
    String token        = "token";
    String clavePrivada = "clavePrivadaY";

    FirmarDTO input = new FirmarDTO(documentoTxt, token);
    Usuario usuario = new Usuario();
    usuario.setClavePrivada(clavePrivada);
    usuario.setToken(token);

    when(usuarioService.obtenerUsuarioByToken(eq(token)))
        .thenReturn(usuario);

    when(firmaService.firmarDocumento(
            eq(clavePrivada), any(DocumentoDTO.class)))
        .thenThrow(new Exception("Fallo interno"));

    try (MockedStatic<JwtUtil> jwtMock = Mockito.mockStatic(JwtUtil.class)) {
            jwtMock
                .when(() -> JwtUtil.validateToken(token))
                .thenReturn(true);

        ResponseEntity<FirmaDTO> response =
            signatureRestController.firmarDocumento(input);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        FirmaDTO body = response.getBody();
        assertNotNull(body, "El body de la respuesta no debe ser null");

        assertEquals(
            "Error al firmar el documento",
            body.getFirma()
        );
    }
}


    @Test
    void testVerificarFirma_Correcto() throws Exception {
        VerificacionDTO dto = new VerificacionDTO();
        dto.setDocumento("documento a verificar");
        dto.setFirma("firmaGenerada");
        dto.setClavePublica("clavePublica123");

        
        String clavePublicaBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A...";
        Usuario usuario = new Usuario(1L, "prueba", null, clavePublicaBase64);
        when(usuarioRepository.findByClavePublica("clavePublica123"))
            .thenReturn(usuario);



            when(firmaService.verificarFirma(eq(dto), eq(clavePublicaBase64)))
                .thenReturn(true);

            ResponseEntity<String> response = signatureRestController.verificar(dto);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Firma verificada correctamente", response.getBody());
        }
    

    @Test
    void testVerificarFirma_NoVerificada() throws Exception {
        VerificacionDTO dto = new VerificacionDTO("doc", "firmaX", "clavePublica");
        Usuario usuario = new Usuario(3L, "usr2", null, "clavePubX");
        when(usuarioRepository.findByClavePublica("clavePublica")).thenReturn(usuario);



            ResponseEntity<String> resp = signatureRestController.verificar(dto);
            assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
            assertEquals("Firma no verificada", resp.getBody());
        
    }
        
}