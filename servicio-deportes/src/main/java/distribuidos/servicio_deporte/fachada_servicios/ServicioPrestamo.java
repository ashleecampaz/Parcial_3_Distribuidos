/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distribuidos.servicio_deporte.fachada_servicios;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidos.servicio_deporte.acceso_a_datos.repositorio.RepositorioPrestamos;
import distribuidos.servicio_deporte.controladores.controladorExcepciones.excepciones.CampoObligatorioVacioException;
import distribuidos.servicio_deporte.controladores.controladorExcepciones.excepciones.FechasInvalidasException;
import distribuidos.servicio_deporte.fachada_servicios.DTO.Peticion.PrestamoDTOPeticion;
import distribuidos.servicio_deporte.fachada_servicios.DTO.Respuesta.PrestamoDTORespuesta;
import distribuidos.servicio_deporte.fachada_servicios.mapper.Mapper; 
import distribuidos.servicio_deporte.acceso_a_datos.modelo.Prestamo; 
/**
 *
 * @author ashle
 */
@Service
public class ServicioPrestamo implements ServicioPrestamoInt{
    @Autowired
    private RepositorioPrestamos repositorio; 
    
    @Autowired
    private Mapper mapper; 

    public PrestamoDTORespuesta agregarPrestamo(PrestamoDTOPeticion p){
        if( p.getImplemento()==null || p.getImplemento().isBlank() 
        || p.getCodigoEstudiante() == 0){
            throw new CampoObligatorioVacioException("Campo obligatorio vacio"); 
        }
        if(!p.getFechaPrestamo().equals(null) && p.getFechaDevolucionReal() != null){
            if(p.getFechaPrestamo().isAfter(p.getFechaDevolucionReal())){
                throw new FechasInvalidasException("La fecha de devolucion no puede ser anterior a la fecha de prestamo.");
            }
        }

        if(p.getFechaDevolucionEstimada() != null && p.getFechaDevolucionReal() !=null){
            if(p.getFechaPrestamo().isAfter(p.getFechaDevolucionEstimada())){
                throw new FechasInvalidasException("La fecha de devolucion estimada no puede ser anterior a la fecha de prestamo.");
            }
        }
        Prestamo prestamo = mapper.mapearDTOPeticionAprestamo(p);
        repositorio.agregarPrestamo(prestamo);
        return mapper.mapearPrestamoADTORespuesta(prestamo); 
    }

    public int  eliminarPrestamoPorCodigoEstudiante(int codigoEstudiante){
        return  repositorio.eliminarPrestamoPorCodigoEstudiante(codigoEstudiante);
    }

    public List<PrestamoDTORespuesta> consultarPrestamosEstudiante(int codigoEstudiante){
        List<Prestamo> prestamos = repositorio.obtenerPrestamosEstudiante(codigoEstudiante);
        List<PrestamoDTORespuesta> prestamosDTO = new ArrayList();
        for (Prestamo p: prestamos){
            prestamosDTO.add(mapper.mapearPrestamoADTORespuesta(p));
        } 
        return prestamosDTO;
    }

    public List<PrestamoDTORespuesta> consultarPrestamos(){
         List<Prestamo> prestamos = repositorio.getPrestamos();
        List<PrestamoDTORespuesta> prestamosDTO = new ArrayList();
        for (Prestamo p: prestamos){
            prestamosDTO.add(mapper.mapearPrestamoADTORespuesta(p));
        } 
        return prestamosDTO;
    }

    
}
