package com.firmaDoc.restController;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firmaDoc.domain.DTO.DocumentoDTO;
import com.firmaDoc.domain.DTO.FirmaDTO;
import com.firmaDoc.domain.DTO.FirmarDTO;
import com.firmaDoc.domain.DTO.VerificacionDTO;
import com.firmaDoc.domain.entity.Usuario;
import com.firmaDoc.repository.UsuarioRepository;
import com.firmaDoc.service.FirmaService;
import com.firmaDoc.service.UsuarioService;
import com.firmaDoc.Utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Firma Digital", description = "Operaciones para firmar y verificar documentos digitalmente")
public class signatureRestController {
    
    private FirmaService firmaService;
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;


    /**
     * @param firmaService
     * @throws NoSuchAlgorithmException
     */
    


    public signatureRestController(FirmaService firmaService,UsuarioService usuarioService,UsuarioRepository usuarioRepository)throws NoSuchAlgorithmException {

        this.firmaService = firmaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;        
    }

    /**
     * Firma digitalmente un documento usando la clave privada del usuario.
     * 
     * @param documento Objeto que contiene el documento a firmar.
     * @return 201 si se ha firmado correctamente, 400 en caso contrario.
     * @throws Exception Si ocurre un error durante la firma.
     */


    @PostMapping("/firmar")
    @Operation(summary = "Genera un par de claves RSA para la firma digital",description = "Devuelve la firma digital en base64 del documento enviado.")
    @ApiResponse(responseCode = "201", description = "Documento firmado correctamente")
    @ApiResponse(responseCode = "400", description = "Error al firmar el documento")
    
    public ResponseEntity<FirmaDTO> firmarDocumento(@RequestBody FirmarDTO firmarDTO)throws Exception{
        
        try{
                Usuario usuario = usuarioService.obtenerUsuarioByClavePublica(firmarDTO.getClavePublica());
                DocumentoDTO documentoDTO = new DocumentoDTO(firmarDTO.getDocumento());

                String firmaBase64 = firmaService.firmarDocumento(usuario.getClavePrivada(), documentoDTO);

                if(firmaBase64 == null || firmaBase64.isEmpty()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FirmaDTO("Error al firmar el documento"));
                }else{
                    return ResponseEntity.status(HttpStatus.CREATED).body(new FirmaDTO(firmaBase64));
                }
                
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FirmaDTO("Error al firmar el documento"));
        }


    }

    /**
     * Verifica la firma digital de un documento usando la clave pública.
     * @param verificacion Objeto que contiene el documento firmado y la firma digital generada previamente.
     * @return 200 si la verificación ha sido correcta, 400 en caso contrario.
     * @throws Exception Si ocurre un error durante la verificación.
     */

    @PostMapping("/verificar")
    @Operation(summary = "Verifica la firma digital de un documento", description = "Verifica la firma digital del documento enviado.")
    @ApiResponse(responseCode = "200", description = "Firma verificada correctamente")
    @ApiResponse(responseCode = "400", description = "Error al verificar la firma")
    public ResponseEntity<String> verificar(@RequestBody VerificacionDTO verificacion)throws Exception{
        
        

        try{

            Usuario usuario = usuarioRepository.findByToken(verificacion.getToken());
            
            if(usuario != null && JwtUtil.validateToken(verificacion.getToken())){

                Boolean verificado = firmaService.verificarFirma(verificacion, usuario.getClavePublica());
                if(verificado){
                    return ResponseEntity.ok().body("Firma verificada correctamente");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firma no verificada");
                }  

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no válido, genera uno nuevo");
            }

            
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al verificar la firma");
        }

    }


}
