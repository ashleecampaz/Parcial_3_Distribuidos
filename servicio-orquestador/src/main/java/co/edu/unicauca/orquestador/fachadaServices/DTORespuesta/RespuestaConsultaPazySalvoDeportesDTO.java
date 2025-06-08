package co.edu.unicauca.orquestador.fachadaServices.DTORespuesta;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RespuestaConsultaPazySalvoDeportesDTO {
    private int codigoEstudiante; 
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada; 
    private LocalDate fechaDevolucionReal; 
    private String implemento;
}
