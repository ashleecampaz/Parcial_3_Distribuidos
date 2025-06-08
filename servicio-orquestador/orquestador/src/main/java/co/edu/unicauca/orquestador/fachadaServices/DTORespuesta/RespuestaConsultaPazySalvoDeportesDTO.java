package co.edu.unicauca.orquestador.fachadaServices.DTORespuesta;

import java.util.Date;

import lombok.Data;

@Data
public class RespuestaConsultaPazySalvoDeportesDTO {
    private Date fechaPrestamo;
    private Date fechaDevolucionEstimada;
    private Date fechaDevolucionReal;
    private String implementoDeportivoPrestado;
}
