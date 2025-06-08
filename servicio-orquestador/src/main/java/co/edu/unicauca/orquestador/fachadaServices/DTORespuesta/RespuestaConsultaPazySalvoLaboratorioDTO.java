package co.edu.unicauca.orquestador.fachadaServices.DTORespuesta;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RespuestaConsultaPazySalvoLaboratorioDTO {
    private int codigoEstudiante; 
    private LocalDate fechaPrestamo;
    private String estado; 
    private LocalDate fechaDevolucionEstimada; 
    private LocalDate fechaDevolucionReal; 
    private String equipo; 
}
