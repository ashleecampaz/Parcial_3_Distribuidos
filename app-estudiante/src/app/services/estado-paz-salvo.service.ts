import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { RespuestaConsultaPazySalvoDTO } from '../modelo/RespuestaConsultaPazySalvoDTO';
import { catchError, Observable, throwError } from 'rxjs';
import { PeticionConsultaPazySalvoDTO } from '../modelo/PeticionConsultaPazySalvoDTO';

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
    return this.http.post<RespuestaConsultaPazySalvoDTO>(this.urlEndPoint_asin,peticion,{headers: this.httpHeaders});
  }

  getEstadoPazYSalvoSin(peticion: PeticionConsultaPazySalvoDTO): Observable<RespuestaConsultaPazySalvoDTO> {
    console.log("Obteniendo paz y salvo de manera sincronica");
    return this.http.post<RespuestaConsultaPazySalvoDTO>(this.urlEndPoint_sin, peticion, {headers: this.httpHeaders});
  }  
}
