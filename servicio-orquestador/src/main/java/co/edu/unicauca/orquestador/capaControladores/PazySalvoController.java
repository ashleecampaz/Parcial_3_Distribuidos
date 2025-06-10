package co.edu.unicauca.orquestador.capaControladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping; // Importar DeleteMapping
import org.springframework.web.bind.annotation.PathVariable; // Importar PathVariable
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.http.HttpStatus; // Importar HttpStatus
import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.capaListeners.NotificacionEventoListener;
import co.edu.unicauca.orquestador.fachadaServices.DTOPeticion.PeticionConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.services.ConsultarPazySalvoInt;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PazySalvoController
{
    @Autowired
    private ConsultarPazySalvoInt objFachada;
    @Autowired
private SimpMessagingTemplate simpMessagingTemplate;

@Autowired
  private NotificacionEventoListener notificacionEventoListener;

  @PostMapping("/orquestadorSincrono")
  public RespuestaConsultaPazySalvoDTO orquestadorServiciosSincronicamente(
      @RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
    notificacionEventoListener.notificarSolicitudPazySalvo(objPeticion.getNombreEstudiante(),
objPeticion.getCodigoEstudiante());
    RespuestaConsultaPazySalvoDTO objResultado = this.objFachada.consultarPazySalvo(objPeticion);
    return objResultado;
  }

  @PostMapping("/orquestadorAsincrono")
  public Mono<RespuestaConsultaPazySalvoDTO> orquestadorServiciosAsincronicamente(
      @RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
    notificacionEventoListener.notificarSolicitudPazySalvo(objPeticion.getNombreEstudiante(),
objPeticion.getCodigoEstudiante());
    Mono<RespuestaConsultaPazySalvoDTO> objResultado = this.objFachada.consultarPazySalvoAsincrono(objPeticion);
    return objResultado;
  }

    // Nuevos endpoints para eliminar deudas
    @DeleteMapping("/deudas-laboratorio/eliminar/{codigoEstudiante}")
    public ResponseEntity<String> eliminarDeudasLaboratorio(@PathVariable Integer codigoEstudiante) {
        String mensaje = this.objFachada.eliminarDeudasLaboratorio(codigoEstudiante);
        if (mensaje.startsWith("Error")) {
            return new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @DeleteMapping("/financiera/eliminar/{codigoEstudiante}")
    public ResponseEntity<String> eliminarDeudasFinanciera(@PathVariable Integer codigoEstudiante) {
        String mensaje = this.objFachada.eliminarDeudasFinanciera(codigoEstudiante);
        if (mensaje.startsWith("Error")) {
            return new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @DeleteMapping("/deudas-deporte/eliminar/{codigoEstudiante}")
    public ResponseEntity<String> eliminarDeudasDeporte(@PathVariable Integer codigoEstudiante) {
        String mensaje = this.objFachada.eliminarDeudasDeporte(codigoEstudiante);
        if (mensaje.startsWith("Error")) {
            return new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
}