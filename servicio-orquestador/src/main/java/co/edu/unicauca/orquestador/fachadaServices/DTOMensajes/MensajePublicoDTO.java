package co.edu.unicauca.orquestador.fachadaServices.DTOMensajes;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MensajePublicoDTO {
    private String area;
    private String mensaje;
    private LocalDate fechaGeneracion;
}
