package distribuidos.servicio_deporte.controladores.controladorExcepciones.excepciones;

import distribuidos.servicio_deporte.controladores.controladorExcepciones.estructura.CodigoError;

public class CampoObligatorioVacioException extends RuntimeException {
    
    private final String llaveMensaje;
    private final String codigo;

    public CampoObligatorioVacioException(CodigoError codigo) {
       super(codigo.getCodigo());
       
       this.llaveMensaje = codigo.getLlaveMensaje();
       this.codigo = codigo.getCodigo();
    }
    
    public CampoObligatorioVacioException(String mensaje) {
       super(mensaje);
       
       this.llaveMensaje = CodigoError.CAMPO_OBLIGATORIO_VACIO.getLlaveMensaje();
       this.codigo = CodigoError.CAMPO_OBLIGATORIO_VACIO.getCodigo();
    }
}