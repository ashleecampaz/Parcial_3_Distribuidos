package co.edu.unicauca.orquestador.fachadaServices.DTORespuesta;

import java.util.List;

import lombok.Data;

@Data
public class RespuestaConsultaPazySalvoDTO {
    private int codigoEstudiante;
    private List<RespuestaConsultaPazySalvoLaboratorioDTO> objLaboratorio;
    private List<RespuestaConsultaPazySalvoFinancieraDTO> objFinanciera;
    private List<RespuestaConsultaPazySalvoDeportesDTO> objDeportes;
    private String mensaje;
}
