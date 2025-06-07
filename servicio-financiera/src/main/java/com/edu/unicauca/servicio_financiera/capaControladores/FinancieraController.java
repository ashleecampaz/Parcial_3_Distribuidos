package com.edu.unicauca.servicio_financiera.capaControladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.unicauca.servicio_financiera.fachadaServices.DTO.DTORespuesta;
import com.edu.unicauca.servicio_financiera.fachadaServices.services.FinancieraService;

@RestController
@RequestMapping("/financiera")
public class FinancieraController {
    private final FinancieraService financieraService;

    public FinancieraController(FinancieraService financieraService) {
        this.financieraService = financieraService;
    }

    @PostMapping("/consultarPendientes/{codigoEstudiante}")
    public ResponseEntity<List<DTORespuesta>> consultarPendientes(@PathVariable Integer codigoEstudiante) {
        List<DTORespuesta> deudas = financieraService.consultarDeudasPendientes(codigoEstudiante);
        return new ResponseEntity<>(deudas, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarPendientes/{codigoEstudiante}")
    public ResponseEntity<String> eliminarPendientes(@PathVariable Integer codigoEstudiante) { // Cambiado a Integer
        boolean eliminadas = financieraService.eliminarPendientes(codigoEstudiante);
        if (eliminadas) {
            return new ResponseEntity<>("Deudas pendientes para el estudiante " + codigoEstudiante
                    + " han sido eliminadas (marcadas como pagadas).", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "No se encontraron deudas pendientes para el estudiante " + codigoEstudiante + ".",
                    HttpStatus.NOT_FOUND);
        }
    }

}
