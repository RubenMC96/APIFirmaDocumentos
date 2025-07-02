package com.firmaDoc.service;

import com.firmaDoc.domain.DTO.UsuarioDTO;
import com.firmaDoc.domain.entity.Usuario;

public interface UsuarioService {


    Usuario crearUsuario(String nombre, String contrasena) throws Exception;

    String obtenerToken(String nombre, String contrasena) throws Exception;
    String obtenerClavePublica(String nombre) throws Exception;
    Usuario obtenerUsuarioByToken(String token) throws Exception;
    String obtenerToken(UsuarioDTO usuarioDTO) throws Exception;

    
}
