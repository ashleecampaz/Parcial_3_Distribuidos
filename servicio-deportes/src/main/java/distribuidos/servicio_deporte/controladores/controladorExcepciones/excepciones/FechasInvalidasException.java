package distribuidos.servicio_deporte.controladores.controladorExcepciones.excepciones;

import distribuidos.servicio_deporte.controladores.controladorExcepciones.estructura.CodigoError;

public class FechasInvalidasException extends RuntimeException {

    private final String llaveMensaje;
    private final String codigo;

    public FechasInvalidasException(CodigoError codigo) {
       super(codigo.getCodigo());
       
       this.llaveMensaje = codigo.getLlaveMensaje();
       this.codigo = codigo.getCodigo();
    }
    
    public FechasInvalidasException(String mensaje) {
       super(mensaje);
       
       this.llaveMensaje = CodigoError.FECHAS_INVALIDAS.getLlaveMensaje();
       this.codigo = CodigoError.FECHAS_INVALIDAS.getCodigo();
    }
}