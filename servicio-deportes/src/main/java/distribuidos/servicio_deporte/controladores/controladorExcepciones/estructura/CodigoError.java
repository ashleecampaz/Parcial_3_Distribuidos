/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package distribuidos.servicio_deporte.controladores.controladorExcepciones.estructura; 

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author ashle
 */
@RequiredArgsConstructor
@Getter
public enum CodigoError {
        ERROR_GENERICO("GC-0001", "ERROR GENERICO"),
        FECHAS_INVALIDAS("GC-0002", "ERROR FECHAS INVALIDAS"),
        CAMPO_OBLIGATORIO_VACIO("GC-0003", "ERROR CAMPO OBLIGATORIO VACIO");

        private final String codigo;
        private final String llaveMensaje;
}
