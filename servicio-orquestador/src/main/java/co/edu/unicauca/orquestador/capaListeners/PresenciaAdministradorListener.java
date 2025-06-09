package co.edu.unicauca.orquestador.capaListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import co.edu.unicauca.orquestador.capaAccesoADatos.Repositorio.RepositorioAdministrador;

@Component
public class PresenciaAdministradorListener {
    @Autowired
    private RepositorioAdministrador repositorioAdministradores;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
   
    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String area = accessor.getFirstNativeHeader("area");
        String nickname = accessor.getFirstNativeHeader("nickname");

        if (area != null) {
            System.out.println("El usuario " + area + " se ha conectado con la sesión: " + sessionId);
            this.repositorioAdministradores.addSession(area, nickname, area);
            simpMessagingTemplate.convertAndSend("/mensajeGrupal/notificaciones",
                    area + " se ha conectado.");
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        String nickname = this.repositorioAdministradores.getArea(sessionId);
        this.repositorioAdministradores.removeSession(sessionId);
        if (nickname != null) {
            System.out.println("El usuario " + nickname + " se ha desconectado de la sesión: " + sessionId);
            simpMessagingTemplate.convertAndSend("/mensajeGrupal/notificaciones",
                    nickname + " se ha desconectado.");
        } 
    }
}
