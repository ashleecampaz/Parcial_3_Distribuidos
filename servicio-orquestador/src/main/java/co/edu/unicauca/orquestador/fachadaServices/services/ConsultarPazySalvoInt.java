package co.edu.unicauca.orquestador.fachadaServices.services;

import co.edu.unicauca.orquestador.fachadaServices.DTOPeticion.PeticionConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDTO;
import reactor.core.publisher.Mono;

public interface ConsultarPazySalvoInt {
    public RespuestaConsultaPazySalvoDTO consultarPazySalvo(PeticionConsultaPazySalvoDTO objPeticion);
    public Mono<RespuestaConsultaPazySalvoDTO> consultarPazySalvoAsincrono(PeticionConsultaPazySalvoDTO objPeticion);
    String eliminarDeudasLaboratorio(Integer codigoEstudiante);
    String eliminarDeudasFinanciera(Integer codigoEstudiante);
    String eliminarDeudasDeporte(Integer codigoEstudiante);
}
