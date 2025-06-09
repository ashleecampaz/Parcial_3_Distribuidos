package co.edu.unicauca.orquestador.capaAccesoADatos.Repositorio;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.orquestador.capaAccesoADatos.Modelos.Administrador;

@Repository
public class RepositorioAdministrador {
    private final ConcurrentHashMap<String, Administrador> areaAdministradorMap;

    public RepositorioAdministrador() {
        this.areaAdministradorMap = new ConcurrentHashMap<>();
    }

    public void addSession(String sessionId, String area) {
        areaAdministradorMap.put(area, new Administrador(sessionId, area));
    }

    public void removeSession(String area) {
        areaAdministradorMap.remove(area);
    }

    public Administrador getAdministrador(String area) {
        return areaAdministradorMap.get(area);
    }

    public String getArea(String area) {
        Administrador admin = areaAdministradorMap.get(area);
        return (admin != null) ? admin.getArea() : null;
    }

    public Collection<Administrador> getAllAdministradores() {
        return areaAdministradorMap.values();
    }
}
