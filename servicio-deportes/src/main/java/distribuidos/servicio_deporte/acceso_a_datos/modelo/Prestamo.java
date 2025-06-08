/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distribuidos.servicio_deporte.acceso_a_datos.modelo;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ashle
 */

@Setter
@Getter
public class Prestamo {
    private int codigoEstudiante; 
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEstimada; 
    private LocalDate fechaDevolucionReal; 
    private String implemento; 

    public Prestamo(){
    }

    public Prestamo(int codigoEstudiante, LocalDate fechaPrestamo,
                     LocalDate fechaDevolucionEstimada, LocalDate fechaDevolucionReal,
                     String implementoPrestado){
        this.codigoEstudiante = codigoEstudiante;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.implemento = implementoPrestado; 
    }
    public Prestamo(int codigoEstudiante, LocalDate fechaDevolucionReal,
                     String implementoPrestado){
        this.codigoEstudiante = codigoEstudiante;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEstimada = fechaPrestamo.plusDays(15);
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.implemento = implementoPrestado; 
    }

    public Prestamo(String implementoPrestado, int codigoEstudiante, LocalDate fechaPrestamo,
                    LocalDate fechaDevolucionReal){
        this.codigoEstudiante = codigoEstudiante;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEstimada =fechaPrestamo.plusDays(15) ;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.implemento = implementoPrestado; 
    }

    public Prestamo(int codigoEstudiante,
                     LocalDate fechaDevolucionEstimada, LocalDate fechaDevolucionReal,
                     String implementoPrestado){
        this.codigoEstudiante = codigoEstudiante;
        this.fechaPrestamo = LocalDate.now(); 
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.implemento = implementoPrestado; 
    }
}
