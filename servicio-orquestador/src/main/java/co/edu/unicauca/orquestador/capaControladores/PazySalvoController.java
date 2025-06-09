package co.edu.unicauca.orquestador.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.capaAccesoADatos.Repositorio.RepositorioAdministrador;
import co.edu.unicauca.orquestador.fachadaServices.DTOMensajes.MensajePrivadoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTOMensajes.MensajePublicoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTOPeticion.PeticionConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.services.ConsultarPazySalvoInt;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class PazySalvoController {

    @Autowired
    private ConsultarPazySalvoInt objFachada;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    @PostMapping("/orquestadorSincrono")
    public RespuestaConsultaPazySalvoDTO orquestadorServiciosSincronicamente(
            @RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
        String codigo = String.valueOf(objPeticion.getCodigoEstudiante());
        String nombres = ""; // Falta completar
        String mensaje = "El estudiante con código " + codigo + " y nombres " + nombres
                + " ha realizado una nueva solicitud de paz y salvo.";

        MensajePublicoDTO mensajePublico = new MensajePublicoDTO();
        mensajePublico.setArea("Orquestador");
        mensajePublico.setMensaje("Orquestado: " + mensaje);
        mensajePublico.setFechaGeneracion(LocalDate.now());

        simpMessagingTemplate.convertAndSend("/chatGrupal/salaChatPublica", mensajePublico);

        RespuestaConsultaPazySalvoDTO objResultado = this.objFachada.consultarPazySalvo(objPeticion);
        return objResultado;
    }

    @PostMapping("/orquestadorAsincrono")
    public Mono<RespuestaConsultaPazySalvoDTO> orquestadorServiciosAsincronicamente(
            @RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
        Mono<RespuestaConsultaPazySalvoDTO> objResultado = this.objFachada.consultarPazySalvoAsincrono(objPeticion);
        return objResultado;
    }

    // Mensajes grupales: el cliente envía a /apiChat/enviarGrupal y todos suscritos
    // a /chatGrupal/sala reciben el mensaje
    @MessageMapping("/enviarMensajePublico")
    @SendTo("/chatGrupal/salaChatPublica")
    public MensajePublicoDTO enviarMensajeGrupal(MensajePublicoDTO mensaje) {
        mensaje.setMensaje(mensaje.getArea() + ": " + mensaje.getMensaje());
        mensaje.setFechaGeneracion(LocalDate.now());
        return mensaje; // reenviamos el mensaje a todos suscritos a /chatGrupal/sala
    }

    // Mensajes privados: cliente envía a /apiChat/enviarPrivado, backend envía a
    // usuario específico con sendToUser()
    @MessageMapping("/enviarMensajePrivado")
    public void enviarMensajePrivado(MensajePrivadoDTO mensaje) {
        String mensajeParaEnviar = mensaje.getArea() + ": " + mensaje.getMensaje();
        mensaje.setMensaje(mensajeParaEnviar);
        simpMessagingTemplate.convertAndSend("/chatPrivado/", mensaje);

    }
}
