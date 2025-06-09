package co.edu.unicauca.orquestador.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.capaListeners.NotificacionEventoListener;
import co.edu.unicauca.orquestador.fachadaServices.DTOMensajes.MensajePrivadoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTOMensajes.MensajePublicoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTOPeticion.PeticionConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.services.ConsultarPazySalvoInt;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PazySalvoController {

    @Autowired
    private ConsultarPazySalvoInt objFachada;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificacionEventoListener notificacionEventoListener;

    @PostMapping("/orquestadorSincrono")
    public RespuestaConsultaPazySalvoDTO orquestadorServiciosSincronicamente(
            @RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
        notificacionEventoListener.notificarSolicitudPazySalvo(objPeticion.getNombreEstudiante(), objPeticion.getCodigoEstudiante());
        RespuestaConsultaPazySalvoDTO objResultado = this.objFachada.consultarPazySalvo(objPeticion);
        return objResultado;
    }

    @PostMapping("/orquestadorAsincrono")
    public Mono<RespuestaConsultaPazySalvoDTO> orquestadorServiciosAsincronicamente(
            @RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
        notificacionEventoListener.notificarSolicitudPazySalvo(objPeticion.getNombreEstudiante(), objPeticion.getCodigoEstudiante());
        Mono<RespuestaConsultaPazySalvoDTO> objResultado = this.objFachada.consultarPazySalvoAsincrono(objPeticion);
        return objResultado;
    }


    @MessageMapping("/enviarMensajePublico")
    public MensajePublicoDTO enviarMensajeGrupal(MensajePublicoDTO mensaje) {
        System.out.println("Enviando mensaje grupal: " + mensaje.getMensaje());
        mensaje.setMensaje(mensaje.getMensaje());
        mensaje.setFechaGeneracion(LocalDate.now());
        simpMessagingTemplate.convertAndSend("/mensajeGrupal/salaChatPublica", mensaje);
        return mensaje; // reenviamos el mensaje a todos suscritos a /chatGrupal/sala
    }

    @MessageMapping("/enviarMensajePrivado")
    public void enviarMensajePrivado(MensajePrivadoDTO mensaje) {
        String mensajeParaEnviar = mensaje.getMensaje();
        mensaje.setMensaje(mensajeParaEnviar);
        simpMessagingTemplate.convertAndSend("/mensajePrivado/"+mensaje.getArea(), mensaje);
    }
}
