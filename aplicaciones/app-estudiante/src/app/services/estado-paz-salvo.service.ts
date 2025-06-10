import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { RespuestaConsultaPazySalvoDTO } from '../modelo/RespuestaConsultaPazySalvoDTO';
import { catchError, Observable, throwError, timer } from 'rxjs';
import { PeticionConsultaPazySalvoDTO } from '../modelo/PeticionConsultaPazySalvoDTO';
import { timeout, retryWhen, delayWhen, scan } from 'rxjs/operators';
 
@Injectable({
  providedIn: 'root'
})
export class EstadoPazSalvoService {
 
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  private urlEndPoint_asin: string = 'http://localhost:8083/api/orquestadorAsincrono';
  private urlEndPoint_sin: string = 'http://localhost:8083/api/orquestadorSincrono';
  private urlEndPoint_eliminar_laboratorio: string = 'http://localhost:8083/api/deudas-laboratorio/eliminar/';
  private urlEndPoint_eliminar_financiera: string = 'http://localhost:8083/api/financiera/eliminar/';
  private urlEndPoint_eliminar_deporte: string = 'http://localhost:8083/api/deudas-deporte/eliminar/';

  constructor(private http: HttpClient) { }
 
  getEstadoPazYSalvoAsin(peticion: PeticionConsultaPazySalvoDTO): Observable<RespuestaConsultaPazySalvoDTO> {
    console.log("Obteniendo paz y salvo de manera asincronica");
    return this.http.post<RespuestaConsultaPazySalvoDTO>(this.urlEndPoint_asin,peticion,{headers: this.httpHeaders}).pipe(
      timeout(5000), 
      retryWhen(errors =>
        errors.pipe(
          scan((reintentos, error) => {
            console.warn(`Reintento #${reintentos +1} fallido.`);
            if (reintentos >= 2) {
              throw error;
            }
            return reintentos + 1;
          }, 0),
          delayWhen(() => {
            console.log("Esperando 4 segundos antes de reintentar...");
            return timer(4000);
          })
        )
      )
    );
  }
 
  // === SINCRÓNICO con control de simulación de fallo ===
  getEstadoPazYSalvoSin(peticion: PeticionConsultaPazySalvoDTO, simularFallo: boolean = false, callbackReintento?: (intento: number) => void
        ): Observable<RespuestaConsultaPazySalvoDTO> {
          
  console.log("Obteniendo paz y salvo de manera sincronica");
  const url = `${this.urlEndPoint_sin}?simularFallo=${simularFallo}`;
  return this.http.post<RespuestaConsultaPazySalvoDTO>(url, peticion, {
    headers: this.httpHeaders
  }).pipe(
    timeout(5000),
    retryWhen(errors =>
      errors.pipe(
        scan((reintentos, error) => {
          const nuevoIntento = reintentos + 1;
          console.warn(`Reintento #${nuevoIntento} fallido.`);
          if (callbackReintento) callbackReintento(nuevoIntento);
          if (nuevoIntento >= 3) throw error;
          return nuevoIntento;
        }, 0),
        delayWhen(() => {
          console.log("Esperando 4 segundos antes de reintentar...");
          return timer(4000);
        })
      )
    )
  );
}

  
  // Nuevos métodos para eliminar deudas
  eliminarDeudasLaboratorio(codigoEstudiante: number): Observable<string> {
    return this.http.delete<string>(`${this.urlEndPoint_eliminar_laboratorio}${codigoEstudiante}`, {headers: this.httpHeaders, responseType: 'text' as 'json'}).pipe(
      catchError(this.handleError)
    );
  }

  eliminarDeudasFinanciera(codigoEstudiante: number): Observable<string> {
    return this.http.delete<string>(`${this.urlEndPoint_eliminar_financiera}${codigoEstudiante}`, {headers: this.httpHeaders, responseType: 'text' as 'json'}).pipe(
      catchError(this.handleError)
    );
  }

  eliminarDeudasDeporte(codigoEstudiante: number): Observable<string> {
    return this.http.delete<string>(`${this.urlEndPoint_eliminar_deporte}${codigoEstudiante}`, {headers: this.httpHeaders, responseType: 'text' as 'json'}).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Error del lado del servidor
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}