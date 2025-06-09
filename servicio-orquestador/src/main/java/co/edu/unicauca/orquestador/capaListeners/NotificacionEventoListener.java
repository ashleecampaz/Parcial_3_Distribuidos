package co.edu.unicauca.orquestador.capaListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacionEventoListener {
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notificarSolicitudPazySalvo(String nombreEstudiante, int codigoEstudiante) {
        String mensaje = "El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante + ") ha realizado una nueva solicitud de paz y salvo.";
        System.out.println(mensaje);
        simpMessagingTemplate.convertAndSend("/Administrador/notificaciones", mensaje);
    }
}
