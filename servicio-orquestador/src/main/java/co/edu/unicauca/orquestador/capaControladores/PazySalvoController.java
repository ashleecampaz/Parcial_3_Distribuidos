package co.edu.unicauca.orquestador.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.orquestador.fachadaServices.DTORespuesta.RespuestaConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.DTOPeticion.PeticionConsultaPazySalvoDTO;
import co.edu.unicauca.orquestador.fachadaServices.services.ConsultarPazySalvoInt;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class PazySalvoController {
    
    @Autowired
    private ConsultarPazySalvoInt objFachada;

    @PostMapping("/orquestadorSincrono")
    public RespuestaConsultaPazySalvoDTO orquestadorServiciosSincronicamente (@RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
        RespuestaConsultaPazySalvoDTO objResultado = this.objFachada.consultarPazySalvo(objPeticion);
        return objResultado;
    }

    @PostMapping("/orquestadorAsincrono")
    public Mono<RespuestaConsultaPazySalvoDTO> orquestadorServiciosAsincronicamente(@RequestBody PeticionConsultaPazySalvoDTO objPeticion) {
        Mono<RespuestaConsultaPazySalvoDTO> objResultado = this.objFachada.consultarPazySalvoAsincrono(objPeticion);
        return objResultado;
    }
}
