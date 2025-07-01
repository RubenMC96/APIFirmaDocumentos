package com.firmaDoc.domain.entity;

import io.micrometer.common.lang.NonNull;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USUARIO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    @ApiModelProperty(
      value = "Identificador único autogenerado del usuario",
      example = "1",
      required = true,
      position = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(
      value = "Nombre de usuario único",
      example = "ruben123",
      required = true,
      position = 2
    )
    @Column(unique = true, nullable = false)
    private @NonNull String nombre;

    @ApiModelProperty(
      value = "Contraseña cifrada del usuario",
      example = "$2a$10$7EqJtq98hPqEX7fNZaFWoO",
      required = true,
      position = 3
    )
    @Column(nullable = false)
    private @NonNull String contrasena;

    @ApiModelProperty(
      value = "Clave privada en formato PEM o Base64",
      example = "MIIEvQIBADANBgkqhk…",
      required = false,
      position = 4
    )
    @Lob
    @Column(columnDefinition = "CLOB")
    private String clavePrivada;

    @ApiModelProperty(
      value = "Clave pública en formato PEM o Base64",
      example = "MIIBIjANBgkqhki…",
      required = false,
      position = 5
    )
    @Lob
    @Column(columnDefinition = "CLOB")
    private String clavePublica;

    @ApiModelProperty(
      value = "Token JWT de sesión asignado al usuario",
      example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9…",
      required = false,
      position = 6
    )
    private String token;


    public Usuario(Long id, String nombre, String clavePrivada, String clavePublica){
        this.id = id;
        this.nombre = nombre;
        this.clavePrivada = clavePrivada;
        this.clavePublica = clavePublica;
    }

}
