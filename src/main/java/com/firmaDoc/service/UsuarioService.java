package com.firmaDoc.service;

import com.firmaDoc.domain.DTO.UsuarioDTO;
import com.firmaDoc.domain.entity.Usuario;

public interface UsuarioService {


    Usuario crearUsuario(String nombre, String contrasena) throws Exception;

    String obtenerToken(String nombre, String contrasena) throws Exception;
    String obtenerClavePublica(UsuarioDTO usuarioDTO) throws Exception;
    Usuario obtenerUsuarioByClavePublica(String clavePublica) throws Exception;
    String obtenerToken(UsuarioDTO usuarioDTO) throws Exception;

    
}
