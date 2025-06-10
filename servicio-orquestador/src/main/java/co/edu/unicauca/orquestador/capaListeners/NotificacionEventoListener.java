package co.edu.unicauca.orquestador.capaListeners;

import java.util.Collections; // Importar Collections
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

    public void notificarDeudas(String nombreEstudiante,
                                int codigoEstudiante, String area, List<?> deudas) {
        MensajePrivadoDTO mensajePrivado = new MensajePrivadoDTO();
        mensajePrivado.setNombreEstudiante(nombreEstudiante);
        mensajePrivado.setCodigoEstudiante(codigoEstudiante);

        if (deudas == null || deudas.isEmpty()) {
            // Enviar mensaje de paz y salvo
            mensajePrivado.setMensaje("El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante
                    + ") está en paz y salvo en el área de " + area + ".");
            mensajePrivado.setDeudas(Collections.emptyList()); // Asegurar que deudas no es null
        } else {
            // Enviar mensaje de deudas
            mensajePrivado.setMensaje("El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante
                    + ") tiene deudas en el área de " + area + ".");
            mensajePrivado.setDeudas(deudas);
        }

        simpMessagingTemplate.convertAndSend("/Administrador/" + area, mensajePrivado);
    }

    public void notificarEstadoFinanciero(String nombreEstudiante, int codigoEstudiante, String area, List<?> deudas) {
        MensajePrivadoDTO mensajePrivado = new MensajePrivadoDTO();
        mensajePrivado.setNombreEstudiante(nombreEstudiante);
        mensajePrivado.setCodigoEstudiante(codigoEstudiante);

        if (deudas == null || deudas.isEmpty()) {
            // Enviar mensaje de paz y salvo
            mensajePrivado.setMensaje("El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante
                    + ") está en paz y salvo en el área de " + area + ".");
            mensajePrivado.setDeudas(Collections.emptyList()); // Asegurar que deudas no es null
        } else {
            // Enviar mensaje de deudas
            mensajePrivado.setMensaje("El estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante
                    + ") tiene deudas en el área de " + area + ".");
            mensajePrivado.setDeudas(deudas);
        }

        simpMessagingTemplate.convertAndSend("/Administrador/" + area, mensajePrivado);
    }

public void notificarEliminacionDeudas(String nombreEstudiante, int codigoEstudiante, String area) {
        MensajePrivadoDTO mensajePrivado = new MensajePrivadoDTO();
        mensajePrivado.setNombreEstudiante(nombreEstudiante); // ¡Aquí faltaba esto!
        mensajePrivado.setCodigoEstudiante(codigoEstudiante);
        mensajePrivado.setMensaje("Se han eliminado deudas para el estudiante " + nombreEstudiante + " (Código: " + codigoEstudiante + ") en el área de " + area + ". Ahora está en paz y salvo en esta área.");
        mensajePrivado.setDeudas(Collections.emptyList()); // No hay deudas específicas para listar aquí, pero mantenemos la propiedad

        System.out.println(mensajePrivado.getMensaje());
        simpMessagingTemplate.convertAndSend("/Administrador/" + area, mensajePrivado);
    }
}