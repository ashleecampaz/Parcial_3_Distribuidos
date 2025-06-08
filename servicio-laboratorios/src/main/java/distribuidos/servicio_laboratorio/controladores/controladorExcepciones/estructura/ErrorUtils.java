/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distribuidos.servicio_laboratorio.controladores.controladorExcepciones.estructura;


/**
 *
 * @author ashle
 */
public final class ErrorUtils {
    
  ErrorUtils() {

  }

  /**
   * Crea un nuevo objeto de <code>Error</code>
   * 
   * @param codigoError
   * @param llaveMensaje
   * @param codigoHttp
   * @return - Objeto creado
   */
  public static Error crearError(final String codigoError, final String llaveMensaje, final Integer codigoHttp) {
    final Error error = new Error();
    error.setCodigoError(codigoError);
    error.setMensaje(llaveMensaje);
    error.setCodigoHttp(codigoHttp);
    return error;
  }
}
