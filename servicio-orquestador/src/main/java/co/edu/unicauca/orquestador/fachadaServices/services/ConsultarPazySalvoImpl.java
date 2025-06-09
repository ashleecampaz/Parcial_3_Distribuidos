package co.edu.unicauca.orquestador.fachadaServices.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import co.edu.unicauca.orquestador.capaListeners.NotificacionEventoListener;
import co.edu.unicauca.orquestador.fachadaServices.DTOPeticion.PeticionConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDeportesDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoFinancieraDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoLaboratorioDTO;
import reactor.core.publisher.Mono;

@Service
public class ConsultarPazySalvoImpl implements ConsultarPazySalvoInt {

        @Autowired
        private WebClient webClient;

        @Autowired
        private NotificacionEventoListener notificacionEventoListener;

        @Override
        public RespuestaConsultaPazySalvoDTO consultarPazySalvo(PeticionConsultaPazySalvoDTO objPeticion) {
                System.out.println("Iniciando consulta de paz y salvo para el estudiante: "
                                + objPeticion.getCodigoEstudiante()
                                + " de manera sincrona...");
                RespuestaConsultaPazySalvoDTO objRespuestaConsultaPazySalvo = new RespuestaConsultaPazySalvoDTO();

                try {
                        // Llamar al serivicio del área de laboratorios
                        String urlServicioLaboratorio = "http://localhost:8082/api/deudas-laboratorio/listar/"
                                        + objPeticion.getCodigoEstudiante();
                        List<RespuestaConsultaPazySalvoLaboratorioDTO> objRespuestaLaboratorio = webClient.get()
                                        .uri(urlServicioLaboratorio)
                                        .retrieve()
                                        .bodyToFlux(RespuestaConsultaPazySalvoLaboratorioDTO.class)
                                        .collectList()
                                        .block(); // Síncrono
                        objRespuestaConsultaPazySalvo.setObjLaboratorio(objRespuestaLaboratorio);

                        // Llamar al servicio del área financiera
                        String urlServicioFinanciera = "http://localhost:8080/financiera/consultarPendientes/"
                                        + objPeticion.getCodigoEstudiante();
                        List<RespuestaConsultaPazySalvoFinancieraDTO> objRespuestaFinanciera = webClient.post()
                                        .uri(urlServicioFinanciera)
                                        .bodyValue(objPeticion)
                                        .retrieve()
                                        .bodyToFlux(RespuestaConsultaPazySalvoFinancieraDTO.class)
                                        .collectList()
                                        .block(); // Síncrono
                        objRespuestaConsultaPazySalvo.setObjFinanciera(objRespuestaFinanciera);

                        // Llamar al servicio del área de deportes
                        String urlServicioDesportes = "http://localhost:8081/api/deudas-deporte/listar/"
                                        + objPeticion.getCodigoEstudiante();
                        List<RespuestaConsultaPazySalvoDeportesDTO> objRespuestaDeportes = webClient.get()
                                        .uri(urlServicioDesportes)
                                        .retrieve()
                                        .bodyToFlux(RespuestaConsultaPazySalvoDeportesDTO.class)
                                        .collectList()
                                        .block(); // Síncrono
                        objRespuestaConsultaPazySalvo.setObjDeportes(objRespuestaDeportes);

                        // Notificar deudas si existen
                        if (objRespuestaLaboratorio != null && !objRespuestaLaboratorio.isEmpty()) {
                                notificacionEventoListener.notificarDeudas(
                                        objPeticion.getNombreEstudiante(),
                                        objPeticion.getCodigoEstudiante(),
                                        "laboratorio",
                                        objRespuestaLaboratorio);
                        }
                        if (objRespuestaFinanciera != null && !objRespuestaFinanciera.isEmpty()) {
                                notificacionEventoListener.notificarDeudas(
                                                objPeticion.getNombreEstudiante(),
                                                objPeticion.getCodigoEstudiante(),
                                                "financiera",
                                                objRespuestaFinanciera);
                        }
                        if (objRespuestaDeportes != null && !objRespuestaDeportes.isEmpty()) {
                                notificacionEventoListener.notificarDeudas(
                                                objPeticion.getNombreEstudiante(),
                                                objPeticion.getCodigoEstudiante(),
                                                "deportes",
                                                objRespuestaDeportes);
                        }

                        objRespuestaConsultaPazySalvo.setCodigoEstudiante(objPeticion.getCodigoEstudiante());
                        objRespuestaConsultaPazySalvo
                                        .setMensaje("Consulta de paz y salvo realizada con éxito con composición sincrona.");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        objRespuestaConsultaPazySalvo.setMensaje("Error al consultar el paz y salvo");
                        // Preguntar si hay que hacer algo parecido a cancelar reservas parciales.
                }

                return objRespuestaConsultaPazySalvo;
        }

        @Override
        public Mono<RespuestaConsultaPazySalvoDTO> consultarPazySalvoAsincrono(
                        PeticionConsultaPazySalvoDTO objPeticion) {
                System.out.println("Iniciando consulta de paz y salvo para el estudiante: "
                                + objPeticion.getCodigoEstudiante()
                                + " de manera asíncrona...");
                RespuestaConsultaPazySalvoDTO objRespuestaConsultaPazySalvo = new RespuestaConsultaPazySalvoDTO();

                // Llamar al servicio del área de laboratorios
                String urlServicioLaboratorio = "http://localhost:8082/api/deudas-laboratorio/listar/"
                                + objPeticion.getCodigoEstudiante();
                ;
                Mono<List<RespuestaConsultaPazySalvoLaboratorioDTO>> objRespuestaLaboratorio = webClient.get()
                                .uri(urlServicioLaboratorio)
                                .retrieve()
                                .bodyToFlux(RespuestaConsultaPazySalvoLaboratorioDTO.class)
                                .collectList()
                                .doOnError(e -> System.err.println(
                                                "Error consultando en el área de laboratorios" + e.getMessage()));

                // Llamar al servicio del área financiera
                String urlServicioFinanciera = "http://localhost:8080/financiera/consultarPendientes/"
                                + objPeticion.getCodigoEstudiante();
                Mono<List<RespuestaConsultaPazySalvoFinancieraDTO>> objRespuestaFinanciera = webClient.post()
                                .uri(urlServicioFinanciera)
                                .bodyValue(objPeticion)
                                .retrieve()
                                .bodyToFlux(RespuestaConsultaPazySalvoFinancieraDTO.class)
                                .collectList()
                                .doOnError(e -> System.err
                                                .println("Error consultando en el área financiera" + e.getMessage()));

                // Llamar al servicio del área de deportes
                String urlServicioDesportes = "http://localhost:8081/api/deudas-deporte/listar/"
                                + objPeticion.getCodigoEstudiante();
                ;
                Mono<List<RespuestaConsultaPazySalvoDeportesDTO>> objRespuestaDeportes = webClient.get()
                                .uri(urlServicioDesportes)
                                .retrieve()
                                .bodyToFlux(RespuestaConsultaPazySalvoDeportesDTO.class)
                                .collectList()
                                .doOnError(e -> System.err
                                                .println("Error consultando en el área de deportes" + e.getMessage()));

                // Composición de las respuestas
                return Mono.zip(objRespuestaLaboratorio, objRespuestaFinanciera, objRespuestaDeportes)
                                .map(results -> {
                                        objRespuestaConsultaPazySalvo.setObjLaboratorio(results.getT1());
                                        objRespuestaConsultaPazySalvo.setObjFinanciera(results.getT2());
                                        objRespuestaConsultaPazySalvo.setObjDeportes(results.getT3());
                                        objRespuestaConsultaPazySalvo
                                                        .setCodigoEstudiante(objPeticion.getCodigoEstudiante());
                                        objRespuestaConsultaPazySalvo.setMensaje(
                                                        "Consulta de paz y salvo realizada con éxito con composición asíncrona.");
                                        return objRespuestaConsultaPazySalvo;
                                })
                                .onErrorResume(error -> {
                                        RespuestaConsultaPazySalvoDTO respuesta = new RespuestaConsultaPazySalvoDTO();
                                        // Preguntar si hay que hacer algo parecido a cancelar reservas parciales.
                                        respuesta.setCodigoEstudiante(objPeticion.getCodigoEstudiante());
                                        respuesta.setMensaje(
                                                        "Error al consultar el paz y salvo: " + error.getMessage());
                                        return Mono.just(respuesta);
                                });
                /*
                 * return objRespuestaFinanciera.map(financiera -> {
                 * objRespuestaConsultaPazySalvo.setObjFinanciera(financiera);
                 * objRespuestaConsultaPazySalvo.setObjLaboratorio(null);
                 * objRespuestaConsultaPazySalvo.setObjDeportes(null);
                 * objRespuestaConsultaPazySalvo.setCodigoEstudiante(objPeticion.
                 * getCodigoEstudiante());
                 * objRespuestaConsultaPazySalvo.setMensaje(
                 * "Consulta de paz y salvo realizada con éxito con composición asíncrona.");
                 * return objRespuestaConsultaPazySalvo;
                 * }).onErrorResume(error -> {
                 * RespuestaConsultaPazySalvoDTO respuesta = new
                 * RespuestaConsultaPazySalvoDTO();
                 * respuesta.setCodigoEstudiante(objPeticion.getCodigoEstudiante());
                 * respuesta.setMensaje(
                 * "Error al consultar el paz y salvo: " + error.getMessage());
                 * return Mono.just(respuesta);
                 * });
                 */
        }

}
