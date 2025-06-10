package co.edu.unicauca.orquestador.fachadaServices.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
            // Notificar SIEMPRE, el listener decidirá si hay deudas o está en paz y salvo
            notificacionEventoListener.notificarDeudas(
                    objPeticion.getNombreEstudiante(),
                    objPeticion.getCodigoEstudiante(),
                    "laboratorio",
                    objRespuestaLaboratorio);

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
            // Notificar SIEMPRE
            notificacionEventoListener.notificarDeudas(
                    objPeticion.getNombreEstudiante(),
                    objPeticion.getCodigoEstudiante(),
                    "financiera",
                    objRespuestaFinanciera);

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
            // Notificar SIEMPRE
            notificacionEventoListener.notificarDeudas(
                    objPeticion.getNombreEstudiante(),
                    objPeticion.getCodigoEstudiante(),
                    "deportes",
                    objRespuestaDeportes);

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
        Mono<List<RespuestaConsultaPazySalvoLaboratorioDTO>> objRespuestaLaboratorio = webClient.get()
                .uri(urlServicioLaboratorio)
                .retrieve()
                .bodyToFlux(RespuestaConsultaPazySalvoLaboratorioDTO.class)
                .collectList()
                .doOnSuccess(deudas -> notificacionEventoListener.notificarDeudas( // Notificar aquí
                        objPeticion.getNombreEstudiante(),
                        objPeticion.getCodigoEstudiante(),
                        "laboratorio",
                        deudas))
                .doOnError(e -> System.err.println(
                        "Error consultando en el área de laboratorios" + e.getMessage()))
                .onErrorReturn(List.of()); // Devuelve una lista vacía en caso de error para no romper el zip

        // Llamar al servicio del área financiera
        String urlServicioFinanciera = "http://localhost:8080/financiera/consultarPendientes/"
                + objPeticion.getCodigoEstudiante();
        Mono<List<RespuestaConsultaPazySalvoFinancieraDTO>> objRespuestaFinanciera = webClient.post()
                .uri(urlServicioFinanciera)
                .bodyValue(objPeticion)
                .retrieve()
                .bodyToFlux(RespuestaConsultaPazySalvoFinancieraDTO.class)
                .collectList()
                .doOnSuccess(deudas -> notificacionEventoListener.notificarDeudas( // Notificar aquí
                        objPeticion.getNombreEstudiante(),
                        objPeticion.getCodigoEstudiante(),
                        "financiera",
                        deudas))
                .doOnError(e -> System.err
                        .println("Error consultando en el área financiera" + e.getMessage()))
                .onErrorReturn(List.of()); // Devuelve una lista vacía en caso de error para no romper el zip

        // Llamar al servicio del área de deportes
        String urlServicioDesportes = "http://localhost:8081/api/deudas-deporte/listar/"
                + objPeticion.getCodigoEstudiante();
        Mono<List<RespuestaConsultaPazySalvoDeportesDTO>> objRespuestaDeportes = webClient.get()
                .uri(urlServicioDesportes)
                .retrieve()
                .bodyToFlux(RespuestaConsultaPazySalvoDeportesDTO.class)
                .collectList()
                .doOnSuccess(deudas -> notificacionEventoListener.notificarDeudas( // Notificar aquí
                        objPeticion.getNombreEstudiante(),
                        objPeticion.getCodigoEstudiante(),
                        "deportes",
                        deudas))
                .doOnError(e -> System.err
                        .println("Error consultando en el área de deportes" + e.getMessage()))
                .onErrorReturn(List.of()); // Devuelve una lista vacía en caso de error para no romper el zip

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

                    // *** NOTA: La notificación para deudas ahora se realiza en doOnSuccess de cada Mono,
                    // *** por lo que no es necesario aquí, ya que el listener es quien determina
                    // *** si es paz y salvo o tiene deudas.
                    return objRespuestaConsultaPazySalvo;
                })
                .onErrorResume(error -> {
                    RespuestaConsultaPazySalvoDTO respuesta = new RespuestaConsultaPazySalvoDTO();
                    respuesta.setCodigoEstudiante(objPeticion.getCodigoEstudiante());
                    respuesta.setMensaje(
                            "Error al consultar el paz y salvo: " + error.getMessage());
                    return Mono.just(respuesta);
                });
    }

    @Override
    public String eliminarDeudasLaboratorio(Integer codigoEstudiante) {
        String urlServicioLaboratorio = "http://localhost:8082/api/deudas-laboratorio/eliminar-todos/" + codigoEstudiante;
        try {
            // Primero, obtener el nombre del estudiante si no lo tenemos en este scope.
            // Para fines de este ejemplo, asumiremos que PeticionConsultaPazySalvoDTO o similar
            // puede proporcionar el nombre o que se obtiene de otro lugar.
            // Si no lo tienes, deberás pasarlo como parámetro o recuperarlo aquí.
            // Por ahora, usaré un placeholder "Nombre Desconocido"
            String nombreEstudiante = "Nombre Estudiante"; // <-- ¡IMPORTANTE! Reemplaza esto con el nombre real del estudiante

            webClient.delete()
                    .uri(urlServicioLaboratorio)
                    .retrieve()
                    .bodyToMono(String.class) // Esperamos un String de respuesta
                    .block(); // Síncrono

            notificacionEventoListener.notificarEliminacionDeudas(nombreEstudiante, codigoEstudiante, "laboratorio");
            return "Deudas de laboratorio eliminadas con éxito para el estudiante con código: " + codigoEstudiante;
        } catch (WebClientResponseException e) {
            System.err.println("Error eliminando deudas de laboratorio: " + e.getResponseBodyAsString());
            return "Error al eliminar deudas de laboratorio: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar deudas de laboratorio: " + e.getMessage());
            return "Error inesperado al eliminar deudas de laboratorio: " + e.getMessage();
        }
    }

    @Override
    public String eliminarDeudasFinanciera(Integer codigoEstudiante) {
        String urlServicioFinanciera = "http://localhost:8080/financiera/eliminarPendientes/" + codigoEstudiante;
        try {
            String nombreEstudiante = "Nombre Estudiante"; // <-- ¡IMPORTANTE! Reemplaza esto con el nombre real del estudiante
            webClient.delete()
                    .uri(urlServicioFinanciera)
                    .retrieve()
                    .bodyToMono(String.class) // Esperamos un String de respuesta
                    .block(); // Síncrono

            notificacionEventoListener.notificarEliminacionDeudas(nombreEstudiante, codigoEstudiante, "financiera");
            return "Deudas financieras eliminadas con éxito para el estudiante con código: " + codigoEstudiante;
        } catch (WebClientResponseException e) {
            System.err.println("Error eliminando deudas financieras: " + e.getResponseBodyAsString());
            return "Error al eliminar deudas financieras: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar deudas financieras: " + e.getMessage());
            return "Error inesperado al eliminar deudas financieras: " + e.getMessage();
        }
    }

    @Override
    public String eliminarDeudasDeporte(Integer codigoEstudiante) {
        String urlServicioDeporte = "http://localhost:8081/api/deudas-deporte/eliminar-todos/" + codigoEstudiante;
        try {
            String nombreEstudiante = "Nombre Estudiante"; // <-- ¡IMPORTANTE! Reemplaza esto con el nombre real del estudiante
            webClient.delete()
                    .uri(urlServicioDeporte)
                    .retrieve()
                    .bodyToMono(String.class) // Esperamos un String de respuesta
                    .block(); // Síncrono

            notificacionEventoListener.notificarEliminacionDeudas(nombreEstudiante, codigoEstudiante, "deportes");
            return "Deudas de deporte eliminadas con éxito para el estudiante con código: " + codigoEstudiante;
        } catch (WebClientResponseException e) {
            System.err.println("Error eliminando deudas de deporte: " + e.getResponseBodyAsString());
            return "Error al eliminar deudas de deporte: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar deudas de deporte: " + e.getMessage());
            return "Error inesperado al eliminar deudas de deporte: " + e.getMessage();
        }
    }
}