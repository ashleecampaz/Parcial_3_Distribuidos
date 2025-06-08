package distribuidos.servicio_laboratorio.fachada_servicios;

import java.util.List;

import distribuidos.servicio_laboratorio.fachada_servicios.DTO.Peticion.PrestamoDTOPeticion;
import distribuidos.servicio_laboratorio.fachada_servicios.DTO.Respuesta.PrestamoDTORespuesta;

public interface ServicioPrestamoInt {
    public PrestamoDTORespuesta agregarPrestamo(PrestamoDTOPeticion P);
    public int eliminarPrestamoPorCodigoEstudiante(int codigoEstudiante);
    public List<PrestamoDTORespuesta> consultarPrestamosEstudiante(int codigoEstudiante);
    public List<PrestamoDTORespuesta> consultarPrestamos();
}
