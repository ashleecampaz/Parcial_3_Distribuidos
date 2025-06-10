/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distribuidos.servicio_deporte.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import distribuidos.servicio_deporte.fachada_servicios.ServicioPrestamoInt;
import distribuidos.servicio_deporte.fachada_servicios.DTO.Peticion.PrestamoDTOPeticion;
import distribuidos.servicio_deporte.fachada_servicios.DTO.Respuesta.PrestamoDTORespuesta;
/**
 *
 * @author ashle
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/deudas-deporte")
public class ControladorDeporte {
    
    @Autowired 
    private ServicioPrestamoInt servicioDeporte; 


    @PostMapping("/guardar")
    public ResponseEntity guardarPrestamo(@RequestBody PrestamoDTOPeticion p) {
        PrestamoDTORespuesta prestamo =  servicioDeporte.agregarPrestamo(p);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }


    @DeleteMapping("/eliminar-todos/{codigoEstudiante}")
    public ResponseEntity eliminarTodosPrestamos(@PathVariable int codigoEstudiante){
       int numPrestamosEliminados =  servicioDeporte.eliminarPrestamoPorCodigoEstudiante(codigoEstudiante);
       return ResponseEntity.status(HttpStatus.OK).body("Se eliminaron " + numPrestamosEliminados + " prestamos del estudiante " + codigoEstudiante); 
    }

    @GetMapping("/listar/{codigoEstudiante}")
    public List<PrestamoDTORespuesta>  listarPrestamosEstudiante(@PathVariable int codigoEstudiante){
       return  servicioDeporte.consultarPrestamosEstudiante(codigoEstudiante); 

    }

    @GetMapping("/listar-todos")
    public List<PrestamoDTORespuesta> listarPrestamos() {
       return servicioDeporte.consultarPrestamos(); 
    }
    
    
    
    

}
