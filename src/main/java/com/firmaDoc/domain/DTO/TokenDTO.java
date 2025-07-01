package com.firmaDoc.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

/**
 * DTO utilizado para la firma digital de un documento.
 * Incluye el documento original en base64.
 */

@Schema(
    description = "Token para la identificación del usuario. ")
public class TokenDTO {

    /**
     * Token para la identificación del usuario.
     */

    @Schema(
        description = "Token para la identificación del usuario.")
    private String token;

}
