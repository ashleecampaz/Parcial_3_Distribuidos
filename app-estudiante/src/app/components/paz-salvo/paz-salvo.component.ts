import { Component } from '@angular/core';
import { EstadoPazSalvoService } from '../../services/estado-paz-salvo.service';
import { HttpClientModule } from '@angular/common/http';
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

  constructor(private pazysalvoService: EstadoPazSalvoService) {}

  consultar() {
    this.hora = new Date();
    this.pazysalvoService.getEstadoPazYSalvoSin(this.peticionCodigoEstudiante).subscribe({
      next: (data) => {
        this.respuestaOrquestador = data; 
      },
      error: (err) => {
        this.error = 'Error al consultar el estado. Verifica el código.';
        console.error(err);
      },
    });
  }
}
