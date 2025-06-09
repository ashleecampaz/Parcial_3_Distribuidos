package co.edu.unicauca.orquestador.capaListeners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import co.edu.unicauca.orquestador.fachadaServices.DTOMensajes.MensajePrivadoDTO;

@Component
public class NotificacionEventoListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notificarSolicitudPazySalvo(String nombreEstudiante, int codigoEstudiante) {
        String mensaje = "El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante
                + ") ha realizado una nueva solicitud de paz y salvo.";
        System.out.println(mensaje);
        simpMessagingTemplate.convertAndSend("/Administrador/notificaciones", mensaje);
    }

    public void notificarDeudas(String nombreEstudiante, int codigoEstudiante, String area, List<?> deudas) {
        if (deudas == null || deudas.isEmpty()) {
            return; // No hay deudas, no notificar
        }
        
        MensajePrivadoDTO mensajePrivado = new MensajePrivadoDTO();
        mensajePrivado.setNombreEstudiante(nombreEstudiante);
        mensajePrivado.setCodigoEstudiante(codigoEstudiante);
        mensajePrivado.setMensaje("El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante
                + ") tiene deudas en el área de " + area + ".");

        mensajePrivado.setDeudas(deudas);

        simpMessagingTemplate.convertAndSend("/Administrador/" + area, mensajePrivado);
    }
}
