import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
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

  

  getEstadoPazYSalvoSin(peticion: PeticionConsultaPazySalvoDTO): Observable<RespuestaConsultaPazySalvoDTO> {
    console.log("Obteniendo paz y salvo de manera sincronica");
    return this.http.post<RespuestaConsultaPazySalvoDTO>(this.urlEndPoint_sin, peticion, {headers: this.httpHeaders}).pipe(
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
}
