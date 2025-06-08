package co.edu.unicauca.orquestador.fachadaServices.DTORespuesta;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RespuestaConsultaPazySalvoFinancieraDTO {
    private double montoAdeudado;
    private String motivoDeuda;
    private LocalDate fechaGeneracionDeuda;
    private LocalDate fechaLimitePago;
    private String estadoDeuda;
}
