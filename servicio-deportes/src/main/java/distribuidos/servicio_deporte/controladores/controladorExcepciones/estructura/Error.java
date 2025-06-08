/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distribuidos.servicio_deporte.controladores.controladorExcepciones.estructura; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * @author ashle
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    
  /**
   * Código de error de la aplicación
   */
  private String codigoError;
  /**
   * Mensaje de error generado
   */
  private String mensaje;
  /**
   * Código de estatus http
   */
  private Integer codigoHttp;
  /**
   * Url de la petición que generó el error
   */
  @Accessors(chain = true)
  private String url;
  /**
   * Método de la petición que generó el error
   */
  @Accessors(chain = true)
  private String metodo;
}
