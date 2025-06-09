import { Component } from '@angular/core';
import { PazSalvoComponent } from './components/paz-salvo/paz-salvo.component';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PazSalvoComponent, CommonModule, FormsModule],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'app-estudiante';
}
