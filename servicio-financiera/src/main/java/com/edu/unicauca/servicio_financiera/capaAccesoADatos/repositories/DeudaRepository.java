package com.edu.unicauca.servicio_financiera.capaAccesoADatos.repositories;

import org.springframework.stereotype.Repository;

import com.edu.unicauca.servicio_financiera.capaAccesoADatos.models.Deuda;
import javax.annotation.PostConstruct; // Importa PostConstruct
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeudaRepository {
        private final List<Deuda> deudas = new ArrayList<>();

        // Método que se ejecuta después de la inicialización del bean
        // para precargar algunas deudas en RAM.
        @PostConstruct
        public void init() {
                deudas.add(new Deuda(1001, 50000.0, "Mora en pago de matrícula",
                                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 15), "en mora"));
                                deudas.add(new Deuda(1002, 0.0, "Ninguno", null, null, "pagada"));
                                deudas.add(new Deuda(1004, 20000.0, "Pérdida de material",
                                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 10), "pendiente"));
                                deudas.add(new Deuda(1005, 50000.0, "Mora en pago de matrícula",
                                                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 15), "en mora"));        
        }

        /**
         * Busca todas las deudas pendientes (estado "pendiente" o "en mora") de un
         * estudiante.
         * 
         * @param codigoEstudiante El código del estudiante.
         * @return Una lista de deudas pendientes.
         */
        public List<Deuda> findDeudasPendientesByCodigoEstudiante(Integer codigoEstudiante) {
                System.out.println("Buscando deudas pendientes para el estudiante: " + codigoEstudiante);
                return deudas.stream()
                                .filter(d -> d.getCodigoEstudiante().equals(codigoEstudiante) &&
                                                ("pendiente".equals(d.getEstadoDeuda())
                                                                || "en mora".equals(d.getEstadoDeuda())))
                                .collect(Collectors.toList());
        }

        /**
         * Elimina las deudas pendientes de un estudiante.
         * Esto se simula cambiando el estado a "pagada" para mantener un registro.
         * 
         * @param codigoEstudiante El código del estudiante.
         * @return true si se eliminaron deudas, false en caso contrario.
         */
        public boolean eliminarDeudasPendientes(Integer codigoEstudiante) {
                System.out.println("Eliminando deudas pendientes para el estudiante: " + codigoEstudiante);
                List<Deuda> deudasEliminar = deudas.stream()
                                .filter(d -> d.getCodigoEstudiante().equals(codigoEstudiante) &&
                                                ("pendiente".equals(d.getEstadoDeuda())
                                                                || "en mora".equals(d.getEstadoDeuda())))
                                .collect(Collectors.toList());

                if (deudasEliminar.isEmpty()) {
                        System.out.println(
                                        "No se encontraron deudas pendientes para el estudiante: " + codigoEstudiante);
                        return false;
                }

                // Cambiando el estado a "pagada"
                deudasEliminar.forEach(deuda -> deuda.setEstadoDeuda("pagada"));
                return true;
        }

}
