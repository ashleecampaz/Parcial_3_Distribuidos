package distribuidos.servicio_laboratorio.fachada_servicios.mapper;

import distribuidos.servicio_laboratorio.acceso_a_datos.modelo.Prestamo;
import distribuidos.servicio_laboratorio.fachada_servicios.DTO.Peticion.PrestamoDTOPeticion;
import distribuidos.servicio_laboratorio.fachada_servicios.DTO.Respuesta.PrestamoDTORespuesta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class Mapper {

    @Bean
    public Mapper generarMapper(){
        return new Mapper(); 
    }
    public Prestamo mapearDTOPeticionAprestamo(PrestamoDTOPeticion p){
        if(p.getFechaPrestamo()== null && p.getFechaDevolucionEstimada()==null){
             return new Prestamo(p.getCodigoEstudiante(), p.getFechaDevolucionReal(),  p.getEquipo(), p.getEquipo());
        }
        if(p.getFechaPrestamo()==null){
            return new Prestamo(p.getCodigoEstudiante(), p.getFechaDevolucionEstimada(), p.getEquipo(),  p.getFechaDevolucionReal(), p.getEquipo());
        }
        if(p.getFechaDevolucionEstimada()==null){
            return new Prestamo(p.getEquipo(), p.getCodigoEstudiante(),  p.getEquipo(),  p.getFechaPrestamo(), p.getFechaDevolucionReal());
        }
        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoEstudiante(p.getCodigoEstudiante());
        prestamo.setFechaPrestamo(p.getFechaPrestamo());
        prestamo.setEstado(p.getEstado());
        prestamo.setFechaDevolucionEstimada(p.getFechaDevolucionEstimada());
        prestamo.setFechaDevolucionReal(p.getFechaDevolucionReal());
        prestamo.setEquipo(p.getEquipo());
        return prestamo; 
    }
    
    public PrestamoDTORespuesta mapearPrestamoADTORespuesta(Prestamo p){
        
        return new PrestamoDTORespuesta(p.getCodigoEstudiante(),  p.getFechaPrestamo(), p.getEstado(),
                                p.getFechaDevolucionEstimada(), p.getFechaDevolucionReal(), 
                                  p.getEquipo()); 
    }

   

}
