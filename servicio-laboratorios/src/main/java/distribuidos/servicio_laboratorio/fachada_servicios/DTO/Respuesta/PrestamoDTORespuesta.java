/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distribuidos.servicio_laboratorio.fachada_servicios.DTO.Respuesta;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author ashle
 */
@Data
@AllArgsConstructor
public class PrestamoDTORespuesta {
    private int codigoEstudiante; 
    private LocalDate fechaPrestamo;
    private String estado; 
    private LocalDate fechaDevolucionEstimada; 
    private LocalDate fechaDevolucionReal; 
    private String equipo; 

   
}
