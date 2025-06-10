import { Routes } from '@angular/router';
import { PazSalvoComponent } from './components/paz-salvo/paz-salvo.component';

export const routes: Routes = [
    {path: '', redirectTo: '/estudiante/ConsultarPazYSalvo', pathMatch: 'full'},
    {path: 'estudiante/ConsultarPazYSalvo', component: PazSalvoComponent}
];
