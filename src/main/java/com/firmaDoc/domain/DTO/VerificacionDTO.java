package com.firmaDoc.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
/*
  * DTO utilizado para la verificación de una firma digitar de un documento.
  *Incluye el documento firmado en base64 y la firma generada previamente.
  */
import lombok.Getter;
import lombok.Setter;

@Schema(
    description = "DTO utilizado para verificar la firma digital de un documento. " +
                  "Incluye el documento original en base64 y la firma digital generada."
)
@Getter
@Setter

public class VerificacionDTO {
    
    /**
     * Documento firmado, codificado en base64.
     */
    @Schema(
        description = "Documento original a verificar, codificado en base64.",
        example = "SG9sYSBtdW5kbyE="
    )    private String documento;
    /**
     * Firma generada previamente.
     */
    @Schema(
        description = "Firma digital generada previamente, codificada en base64.",
        example = "MEUCIQDn..."
    )    private String firma;

    /**
     * Token correspondiente al usuario.
     */
    @Schema(
        description = "Token correspondiente al usuario."
    )

    private String clavePublica;

    public VerificacionDTO() {
    }

    /**
     * Constructor con documento y firma.
     * @param documento Documento firmado en base64.
     * @param firma Firma generada previamente
     */
    public VerificacionDTO(String documento, String firma,String clavePublica) {
        this.documento = documento;
        this.firma = firma;
        this.clavePublica = clavePublica;
    }

}
