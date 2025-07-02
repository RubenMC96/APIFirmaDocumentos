package com.firmaDoc.restController;

import java.security.NoSuchAlgorithmException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firmaDoc.domain.DTO.TokenDTO;
import com.firmaDoc.domain.DTO.UsuarioDTO;
import com.firmaDoc.domain.entity.Usuario;
import com.firmaDoc.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Firma Digital", description = "Operaciones para firmar y verificar documentos digitalmente")
public class usuarioRestController {
    
    private UsuarioService usuarioService;


    /**
     * @param usuarioService
     * @throws NoSuchAlgorithmException
     */
    


    public usuarioRestController(UsuarioService usuarioService)throws NoSuchAlgorithmException {

        this.usuarioService = usuarioService;
        
    }

    /**
     * Firma digitalmente un documento usando la clave privada del usuario.
     * 
     * @param usuarioDTO Objeto que contiene los datos del usuario a crear.
     * @return 201 si se ha firmado correctamente, 400 en caso contrario.
     * @throws Exception Si ocurre un error durante la firma.
     */


    @PostMapping("/crearCuenta")
    @Operation(summary = "genera un susuario nuevo",description = "Crea un usuario nuevo con los datos proporcionados por el usuario.")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @ApiResponse(responseCode = "400", description = "Error al crear el usuario")
    
    public ResponseEntity<UsuarioDTO> crearCuenta(@RequestBody UsuarioDTO usuarioDTO)throws Exception{
        
        try{

            Usuario usuario = usuarioService.crearUsuario(usuarioDTO.getNombre(),usuarioDTO.getContrasena());
            if(usuario != null){
                return ResponseEntity.ok().body(new UsuarioDTO(usuario.getNombre()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UsuarioDTO("Error al crear la cuenta"));
            }

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UsuarioDTO("Error al crear la cuenta"));
        }


    }

    /**
     * Verifica la firma digital de un documento usando la clave pública.
     * @param verificacion Objeto que contiene el documento firmado y la firma digital generada previamente.
     * @return 200 si la verificación ha sido correcta, 400 en caso contrario.
     * @throws Exception Si ocurre un error durante la verificación.
     */

    @PostMapping("/generarToken")
    @Operation(summary = "Verifica la firma digital de un documento", description = "Verifica la firma digital del documento enviado.")
    @ApiResponse(responseCode = "200", description = "Token generado correctamente")
    @ApiResponse(responseCode = "400", description = "Error al obtener token")
    public ResponseEntity<TokenDTO> generarToken(@RequestBody UsuarioDTO usuarioDTO)throws Exception{

        try{

            String token = usuarioService.obtenerToken(usuarioDTO.getNombre(),usuarioDTO.getContrasena());

            if(!token.isEmpty()){
                return ResponseEntity.ok().body(new TokenDTO(token));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TokenDTO("Error al obtener token"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TokenDTO("Error al obtener token"));
        }

    }
    /**
     * 
     * @param usuarioDTO Objeto que contiene el usuario y la contraseña para identificarse.
     * @return Devuelve el token y 200 si la obtención del token ha sido correcta, 400 en caso contrario.
     * @throws Exception
     */
    
    @PostMapping("/obtenerToken")
    @ApiResponse(responseCode = "200", description = "Token obtenido correctamente")
    @ApiResponse(responseCode = "400", description = "El usuario no existe o no tiene token generado")
    public ResponseEntity<String> obtenerToken(@RequestBody UsuarioDTO usuarioDTO)throws Exception{

        try{
            String token = usuarioService.obtenerToken(usuarioDTO);
            return ResponseEntity.ok().body("Token: " + token);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe o no tiene token generado");
        }
    }

    /**
     * 
     * @param usuarioDTO Objeto que contiene el usuario y la contraseña para identificarse.
     * @return Devuelve la calve pública y 200 si la obtención de la calve ha sido correcta, 400 en caso contrario.
     * @throws Exception
     */

    @PostMapping("/obtenerClavePublica")
    @ApiResponse(responseCode = "200", description = "Clave pública obtenida correctamente")
    @ApiResponse(responseCode = "400", description = "El usuario no existe")
    public ResponseEntity<String> obtenerClavePublica(@RequestBody UsuarioDTO usuarioDTO)throws Exception{

        try{
            String clavePublica = usuarioService.obtenerClavePublica(usuarioDTO.getNombre());
            return ResponseEntity.ok().body("Clave publica: " + clavePublica);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
        }
    }


        
}
