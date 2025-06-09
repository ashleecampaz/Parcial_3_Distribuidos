import { Component } from '@angular/core';
import { PazysalvoService } from '../../services/estado-paz-salvo.service';

import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-paz-salvo',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './paz-salvo.component.html',
})
export class PazSalvoComponent {
  codigoEstudiante = '';
  resultado: any = null;
  error: string = '';

  constructor(private pazysalvoService: PazysalvoService) {}

  consultar() {
    this.resultado = null;
    this.error = '';

    this.pazysalvoService.consultarEstado(this.codigoEstudiante).subscribe({
      next: (data) => {
        this.resultado = data;
      },
      error: (err) => {
        this.error = 'Error al consultar el estado. Verifica el código.';
        console.error(err);
      },
    });
  }
}
