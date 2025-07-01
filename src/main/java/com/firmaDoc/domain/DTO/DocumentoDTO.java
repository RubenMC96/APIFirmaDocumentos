package com.firmaDoc.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO utilizado para la firma digital de un documento.
 * Incluye el documento original en base64.
 */

@Schema(
    description = "DTO utilizado para la firma digital de un documento. " +
                  "Incluye el documento original en base64."
)
public class DocumentoDTO {
    
    /**
     * Documento a firmar, codificado en base64.
     */

    @Schema(
        description = "Documento a firmar, codificado en base64.",
        example = "SG9sYSBtdW5kbyE="
    )    private String documento;

    public DocumentoDTO() {
    }

    /**
     * Constructor con documento.
     * @param documento Documento a firmar en base64.
     */
    public DocumentoDTO(String documento) {
        this.documento = documento;
    }

    /**
     * Obtiene el documento a firmar.
     * @return Documento en base64.
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Establece el documento a firmar.
     * @param documento Documento en base64.
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
