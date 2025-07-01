package com.firmaDoc.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.firmaDoc.domain.DTO.DocumentoDTO;
import com.firmaDoc.domain.DTO.VerificacionDTO;


@Service
public class FirmaServiceImp implements FirmaService {

    public FirmaServiceImp() {
        // Constructor vacío
    }


    /**
     * Firma un documento usando la clave privada proporcionada.
     *
     * @param keyPair    Par de claves (pública y privada) del usuario.
     * @param documento  Objeto que contiene el documento a firmar.
     * @return           Firma digital del documento en formato Base64.
     * @throws Exception Si ocurre un error durante el proceso de firma.
    */

    @Override
    public String firmarDocumento(String clavePrivada,DocumentoDTO documento) throws Exception {
        
        try{
        byte[] clave = Base64.getDecoder().decode(clavePrivada);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clave);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(documento.getDocumento().getBytes(StandardCharsets.UTF_8));
        byte[] firma = signature.sign();
        return Base64.getEncoder().encodeToString(firma);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al firmar el documento");
        }
         
    }

    /**
     * Verifica la firma digital de un documento usando la clave pública.
     *
     * @param verificacion Objeto que contiene el documento original y la firma digital.
     * @param keyPair      Par de claves (pública y privada) del usuario.
     * @return             true si la firma es válida, false en caso contrario.
     * @throws Exception   Si ocurre un error durante la verificación.
    */

    @Override
    public boolean verificarFirma(VerificacionDTO verificacion, String clavePublica) throws Exception {
        
        try{
        byte[] clave = Base64.getDecoder().decode(clavePublica);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(clave);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(verificacion.getDocumento().getBytes(StandardCharsets.UTF_8));
        byte[] firma = Base64.getDecoder().decode(verificacion.getFirma());
        
        return signature.verify(firma);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al verificar la firma");
        }
    }
    
}
