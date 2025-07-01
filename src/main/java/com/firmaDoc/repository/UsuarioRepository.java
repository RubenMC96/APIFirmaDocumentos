package com.firmaDoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firmaDoc.domain.entity.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

    
    Optional findById(Long id);
    
    Usuario findByNombre(String nombre);

    Usuario findByNombreAndContrasena(String nombre, String contrasena);

    Boolean existsByNombre(String nombre);

    Usuario findByToken(String token);
    Usuario findByClavePublica(String clavePublica);
}
