import { Component } from '@angular/core';
import { EstadoPazSalvoService } from '../../services/estado-paz-salvo.service';
import { HttpClientModule }from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RespuestaConsultaPazySalvoDTO } from '../../modelo/RespuestaConsultaPazySalvoDTO';
import { PeticionConsultaPazySalvoDTO } from '../../modelo/PeticionConsultaPazySalvoDTO';
 
@Component({
  selector: 'app-paz-salvo',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './paz-salvo.component.html',
  styleUrl: './paz-salvo.component.css'
})
export class PazSalvoComponent {
  respuestaOrquestador?: RespuestaConsultaPazySalvoDTO = new RespuestaConsultaPazySalvoDTO(); 
  peticionCodigoEstudiante: PeticionConsultaPazySalvoDTO = new PeticionConsultaPazySalvoDTO();
  hora: Date = new Date();
  resultado: any = null;
  error: string = '';
  cargando: boolean = false;
  numeroReintentos: number = 0;
 
  constructor(private pazysalvoService: EstadoPazSalvoService) {}
 
  get r() {
    return this.respuestaOrquestador;
  }
  consultar() {
  this.hora = new Date();
  this.error = '';
  this.respuestaOrquestador = new RespuestaConsultaPazySalvoDTO();
  this.numeroReintentos = 0;
  this.cargando = true;
  this.hora = new Date();

  this.pazysalvoService.getEstadoPazYSalvoSin(this.peticionCodigoEstudiante,
   false, (intento) => {
    this.numeroReintentos = intento;
  }).subscribe({
      next: (data) => {
        this.respuestaOrquestador = data; 
        this.cargando = false;
        this.error = '';
      },
      error: (err) => {
        this.error = `No se pudo conectar con el orquestador después de ${this.numeroReintentos} intento(s).`;
        this.cargando = false;
        console.error(err);
      },
    });
  }
 
  eliminarDeudasLaboratorio() {
    const codigo = Number(this.peticionCodigoEstudiante.codigoEstudiante);
    this.pazysalvoService.eliminarDeudasLaboratorio(codigo).subscribe({
      next: (mensaje) => {
        console.log(mensaje);
        alert('Deudas de laboratorio eliminadas con éxito.');
        this.consultar(); // Actualizar el estado después de eliminar
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudieron eliminar las deudas de laboratorio.';
      }
    });
  }
 
  eliminarDeudasFinanciera() {
    const codigo = Number(this.peticionCodigoEstudiante.codigoEstudiante);
    this.pazysalvoService.eliminarDeudasFinanciera(codigo).subscribe({
      next: (mensaje) => {
        console.log(mensaje);
        alert('Deudas financieras eliminadas con éxito.');
        this.consultar(); // Actualizar el estado después de eliminar
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudieron eliminar las deudas financieras.';
      }
    });
  }

  eliminarDeudasDeporte() {
    const codigo = Number(this.peticionCodigoEstudiante.codigoEstudiante);
    this.pazysalvoService.eliminarDeudasDeporte(codigo).subscribe({
      next: (mensaje) => {
        console.log(mensaje);
        alert('Deudas de deporte eliminadas con éxito.');
        this.consultar();
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudieron eliminar las deudas de deporte.';
      }
    });
  }
}