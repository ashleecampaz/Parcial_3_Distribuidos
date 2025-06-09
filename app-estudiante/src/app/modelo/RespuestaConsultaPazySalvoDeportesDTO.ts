export class RespuestaConsultaPazySalvoDeportesDTO {
  codigoEstudiante!: number;
  fechaPrestamo!: string;             // formato 'YYYY-MM-DD'
  fechaDevolucionEstimada!: string;   // formato 'YYYY-MM-DD'
  fechaDevolucionReal!: string;       // formato 'YYYY-MM-DD'
  implemento!: string;
}