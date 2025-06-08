package distribuidos.servicio_laboratorio.fachada_servicios.DTO.Peticion;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoDTOPeticion {

    private int codigoEstudiante; 
    private LocalDate fechaPrestamo;
    private String estado; 
    private LocalDate fechaDevolucionEstimada; 
    private LocalDate fechaDevolucionReal; 
    private String equipo; 
}
