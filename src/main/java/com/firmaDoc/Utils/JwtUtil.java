package com.firmaDoc.Utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.firmaDoc.domain.entity.Usuario;
import com.firmaDoc.repository.UsuarioRepository;

import java.util.Date;

@Component
public class JwtUtil {

     private static String SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        SECRET_KEY = secretKey;
    }

    private UsuarioRepository usuarioRepository;

    public static String generarToken(Usuario usuario) {
        
        return Jwts.builder()
                .setSubject(usuario.getNombre())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora de validez
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validar token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener usuario desde el token
    public Long getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        Usuario usuario = usuarioRepository.findByNombre(claims.getSubject());
        return usuario.getId();
    }
}
