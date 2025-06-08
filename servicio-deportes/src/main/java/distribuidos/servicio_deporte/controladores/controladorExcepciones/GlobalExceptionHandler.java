/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distribuidos.servicio_deporte.controladores.controladorExcepciones;


import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import distribuidos.servicio_deporte.controladores.controladorExcepciones.excepciones.CampoObligatorioVacioException;
import distribuidos.servicio_deporte.controladores.controladorExcepciones.excepciones.FechasInvalidasException;

/**
 *
 * @author ashle
 */
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
    
     @ExceptionHandler(CampoObligatorioVacioException.class)
    public ResponseEntity<?> handleCampoObligatorioVacioException(CampoObligatorioVacioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
    
     @ExceptionHandler(FechasInvalidasException.class)
    public ResponseEntity<?> handleFechasInvalidasException(FechasInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
    

}
