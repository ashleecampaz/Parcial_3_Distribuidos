export class RespuestaConsultaPazySalvoFinancieraDTO {
  montoAdeudado!: number;
  motivoDeuda!: string;
  fechaGeneracionDeuda!: string;  // formato 'YYYY-MM-DD'
  fechaLimitePago!: string;       // formato 'YYYY-MM-DD'
  estadoDeuda!: string;
}