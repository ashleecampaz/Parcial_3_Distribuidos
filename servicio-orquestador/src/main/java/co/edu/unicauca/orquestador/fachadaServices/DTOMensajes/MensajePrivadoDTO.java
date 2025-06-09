package co.edu.unicauca.orquestador.fachadaServices.DTOMensajes;

import java.util.List;

import lombok.Data;

@Data
public class MensajePrivadoDTO {
    private String nombreEstudiante;
    private int codigoEstudiante;
    private String mensaje;
    private List<?> deudas; // Lista de deudas asociadas al mensaje, si aplica
}
