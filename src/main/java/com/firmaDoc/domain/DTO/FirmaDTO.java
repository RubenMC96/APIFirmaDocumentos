package com.firmaDoc.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO utilizado para el retorno de la firma digital de un documento.
 * Incluye el la firma digital en base64.
 */

@Schema(
    description = "DTO utilizado para el retorno de la firma digital de un documento. " +
                  "Incluye el la firma digital en base64."
)
public class FirmaDTO {
    
    /**
     * Documento a firmar, codificado en base64.
     */

    @Schema(
        description = "Firma a devolver, codificada en base64.",
        example = "SG9sYSBtdW5kbyE="
    )    private String firma;

    public FirmaDTO() {
    }

    /**
     * Constructor con documento.
     * @param firma Documento a firmar en base64.
     */
    public FirmaDTO(String firma) {
        this.firma = firma;
    }

    /**
     * Obtiene la firmar.
     * @return Firma en base64.
     */
    public String getFirma() {
        return firma;
    }

    /**
     * Establece la firmar.
     * @param firma Firma en base64.
     */
    public void setFirma(String firma) {
        this.firma = firma;
    }
}
