/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distribuidos.servicio_laboratorio.acceso_a_datos.modelo;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ashle
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {
    private int codigoEstudiante; 
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada; 
    private LocalDate fechaDevolucionReal; 
    private String estado; 
    private String equipo; 

   
    public Prestamo(int codigoEstudiante, LocalDate fechaDevolucionReal,    String estado,
                     String equipo){
        this.codigoEstudiante = codigoEstudiante;
        this.estado = estado; 
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEstimada = fechaPrestamo.plusDays(15);
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.equipo = equipo;
    }

    public Prestamo(int codigoEstudiante, LocalDate fechaPrestamo, String estado,
                    LocalDate fechaDevolucionReal, String equipo){
        this.codigoEstudiante = codigoEstudiante;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
        this.fechaDevolucionEstimada =fechaPrestamo.plusDays(15) ;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.equipo = equipo;
    }

    public Prestamo(String equipo, int codigoEstudiante, String estado,
                     LocalDate fechaDevolucionEstimada, LocalDate fechaDevolucionReal){
        this.codigoEstudiante = codigoEstudiante;
        this.fechaPrestamo = LocalDate.now(); 
          this.estado = estado;
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.equipo = equipo;
    }
}
