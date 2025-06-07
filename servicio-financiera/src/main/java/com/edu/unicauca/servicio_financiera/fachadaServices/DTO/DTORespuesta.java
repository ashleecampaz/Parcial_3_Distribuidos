package com.edu.unicauca.servicio_financiera.fachadaServices.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTORespuesta {
    private double montoAdeudado;
    private String motivoDeuda;
    private LocalDate fechaGeneracionDeuda;
    private LocalDate fechaLimitePago;
    private String estadoDeuda; // "pendiente", "pagada", "en mora"


}
