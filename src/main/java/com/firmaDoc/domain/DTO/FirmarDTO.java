package com.firmaDoc.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * DTO utilizado para el retorno de la firma digital de un documento.
 * Incluye el la firma digital en base64.
 */

@Schema(
    description = "DTO utilizado para el envío del documento y la clave pública del usuario para firmar. " +
                  "Incluye el documento en base64."
)
public class FirmarDTO {
    
    /**
     * Documento a firmar, codificado en base64.
     */

    @Schema(
        description = "Documento en base64.",
        example = "JVBERi0xLjQKJfbk/...")
    String documento;
        /**
     * Documento a firmar, codificado en base64.
     */

    @Schema(
        description = "Clave pública.")
    String clavePublica;
}
