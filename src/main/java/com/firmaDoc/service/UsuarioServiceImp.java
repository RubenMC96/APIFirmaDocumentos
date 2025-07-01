package com.firmaDoc.service;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.firmaDoc.domain.DTO.UsuarioDTO;
import com.firmaDoc.domain.entity.Usuario;
import com.firmaDoc.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import com.firmaDoc.Utils.JwtUtil;

@Service
public class UsuarioServiceImp implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImp(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario crearUsuario(String nombre,String contrasena) throws Exception{


        try{
            if(usuarioRepository.existsByNombre(nombre)){
                throw new RuntimeException("El usuario ya existe");
            }else{
                KeyPairGenerator heyGen = KeyPairGenerator.getInstance("RSA");
                heyGen.initialize(2048);
            
                KeyPair keyPair = heyGen.generateKeyPair();

                String clavePublica  = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
                String clavePrivada = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

                String hashedPassword = passwordEncoder.encode(contrasena);
                
                return usuarioRepository.save(new Usuario(null,nombre,hashedPassword,clavePrivada,clavePublica,null));

            }

        }catch (NoSuchAlgorithmException|DataAccessException e) {
            throw new RuntimeException("Error al crear el usurio",e);
        }
        
    }

    public String obtenerToken(String nombre, String contrasena) throws Exception{

        try{
             Usuario usuario = usuarioRepository.findByNombre(nombre);

            if(usuario == null){
                throw new RuntimeException("El usuario no existe");
            }else if(!passwordEncoder.matches(contrasena, usuario.getContrasena())){

                throw new RuntimeException("Contraseña incorrecta");
            }
            String token =  JwtUtil.generarToken(usuario);

            usuario.setToken(token);

            usuarioRepository.save(usuario);

            return token;
                
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al generar token");
        }

    }

    public String obtenerClavePublica(UsuarioDTO usuarioDTO) throws Exception{
        try{
            Usuario usuario = usuarioRepository.findByNombre(usuarioDTO.getNombre());

            if(usuario == null){
                throw new RuntimeException("El usuario no existe");
            }else if(!passwordEncoder.matches(usuarioDTO.getContrasena(), usuario.getContrasena())){

                throw new RuntimeException("Contraseña incorrecta");
            }   
            return usuario.getClavePublica();   
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la calve pública");
        }
    }

        public Usuario obtenerUsuarioByClavePublica(String clavePublica) throws Exception{
        try{
            Usuario usuario = usuarioRepository.findByClavePublica(clavePublica);

            if(usuario == null){
                throw new RuntimeException("El usuario no existe");
            }  
            return usuario;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la calve pública");
        }
    }

    public String obtenerToken(UsuarioDTO usuarioDTO) throws Exception{

        try{
            Usuario usuario = usuarioRepository.findByNombre(usuarioDTO.getNombre());

            if(usuario == null){
                throw new RuntimeException("El usuario no existe");
            }else if(!passwordEncoder.matches(usuarioDTO.getContrasena(), usuario.getContrasena())){

                throw new RuntimeException("Contraseña incorrecta");
            }   
            return usuario.getToken();   
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el token");
        }

    }
    
}
