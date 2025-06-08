
package distribuidos.servicio_deporte.acceso_a_datos.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import distribuidos.servicio_deporte.acceso_a_datos.modelo.Prestamo;

/**
 *
 * @author ashle
 */

@Repository
public class RepositorioPrestamos {

    private List<Prestamo> prestamos;

    public RepositorioPrestamos() {
        this.prestamos = new ArrayList<Prestamo>();
    }

    public List<Prestamo> obtenerPrestamosEstudiante(int codigoEstudiante) {
        List<Prestamo> prestamosEstudiante = new ArrayList<Prestamo>();

        for (Prestamo p : prestamos) {
            if (p.getCodigoEstudiante() == codigoEstudiante) {
                prestamosEstudiante.add(p);
            }
        }
        return prestamosEstudiante;
    }

    public int eliminarPrestamoPorCodigoEstudiante(int codigoEstudiante) {
        int originalSize = this.prestamos.size();
        this.prestamos.removeIf(p -> p.getCodigoEstudiante() == codigoEstudiante);
        return originalSize - this.prestamos.size();
    }

    public Prestamo agregarPrestamo(Prestamo p) {
        prestamos.add(p);
        return p;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
