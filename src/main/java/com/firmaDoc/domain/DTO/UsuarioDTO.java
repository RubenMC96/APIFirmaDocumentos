package com.firmaDoc.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

/**
 * DTO del usuario utilizado para la firma digital de un documento.
 */

@Schema(
    description = "DTO del usuario utilizado para la firma digital de un documento. ")
public class UsuarioDTO {
    
    private Long id;
    private String nombre;
    private String contrasena;
    private String clavePrivada;
    private String clavePublica;    
    private String token;

    /**
     * Constructor con documento.
     * @param nombre Nombre de usuario. Deberá de ser único.
     */
    public UsuarioDTO(String nombre){
        this.nombre = nombre;
    }

    
    /**
     * Constructor con documento.
     * @param nombre Nombre de usuario. Deberá de ser único.
     * @param contrasena Contraseña para la identificación del usuario.
     */
    public UsuarioDTO(String nombre,String contrasena){
        this.nombre = nombre;
        this.contrasena = contrasena;
    }
    

    public Long getId(){
        return this.id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return Documento en base64.
     */
    public String getNombre(){
        return this.nombre;
    }
    /**
     * Establece el nombre del usuario.
     * @param documento Documento en base64.
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    /**
     * Obtiene la contraseña del usuario
     * @return Documento en base64.
     */
    public String getContrasena(){
        return this.contrasena;
    }
    /**
     * Establece la contraseña del usuario.
     * @param documento Documento en base64.
     */
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    /**
     * Obtiene la clave pública del usuario.
     * @return Documento en base64.
     */
    public String getClavePublica(){
        return this.clavePublica;
    }
    /**
     * Establece la clave pública.
     * @param documento Documento en base64.
     */
    public void setClavePublica(String clavePublica){
        this.clavePublica = clavePublica;
    }
    /**
     * Obtiene la clave privada.
     * @return Documento en base64.
     */
    public String getClavePrivada(){
        return this.clavePrivada;
    }
    /**
     * Establece la clave privada.
     * @param documento Documento en base64.
     */
    public void setClavePrivada(String clavePrivada){
        this.clavePrivada = clavePrivada;
    }
    /**
     * Obtiene el token.
     * @return Documento en base64.
     */
    public String getToken(){
        return this.token;
    }
    /**
     * Establece el token.
     * @param documento Documento en base64.
     */
    public void setToken(String token){
        this.token = token;
    }
}
