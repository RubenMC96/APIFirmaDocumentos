package com.firmaDoc.service;


import com.firmaDoc.domain.DTO.DocumentoDTO;
import com.firmaDoc.domain.DTO.VerificacionDTO;




public interface FirmaService {


    String firmarDocumento(String clavePrivada, DocumentoDTO documento) throws Exception;

    boolean verificarFirma(VerificacionDTO verificacion, String clavePublica) throws Exception;
    
}
