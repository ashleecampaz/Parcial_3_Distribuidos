export class RespuestaConsultaPazySalvoLaboratorioDTO {
  codigoEstudiante!: number;
  fechaPrestamo!: string;             // formato 'YYYY-MM-DD'
  estado!: string;
  fechaDevolucionEstimada!: string;   // formato 'YYYY-MM-DD'
  fechaDevolucionReal!: string;       // formato 'YYYY-MM-DD'
  equipo!: string;
}
