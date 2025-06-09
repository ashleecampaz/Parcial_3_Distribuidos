package co.edu.unicauca.orquestador.capaAccesoADatos.Modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
    private String sessionId;
    private String area;
}
