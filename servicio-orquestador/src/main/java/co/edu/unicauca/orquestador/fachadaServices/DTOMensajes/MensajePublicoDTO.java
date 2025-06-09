package co.edu.unicauca.orquestador.fachadaServices.DTOMensajes;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensajePublicoDTO {
    private String area;
    private String mensaje;
    private LocalDate fechaGeneracion;
}
