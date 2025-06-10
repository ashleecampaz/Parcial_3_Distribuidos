import { RespuestaConsultaPazySalvoLaboratorioDTO } from './RespuestaConsultaPazySalvoLaboratorioDTO';
import { RespuestaConsultaPazySalvoFinancieraDTO } from './RespuestaConsultaPazySalvoFinancieraDTO';
import { RespuestaConsultaPazySalvoDeportesDTO } from './RespuestaConsultaPazySalvoDeportesDTO';

export class RespuestaConsultaPazySalvoDTO {
  codigoEstudiante!: number;
  objLaboratorio!: RespuestaConsultaPazySalvoLaboratorioDTO[];
  objFinanciera!: RespuestaConsultaPazySalvoFinancieraDTO[];
  objDeportes!: RespuestaConsultaPazySalvoDeportesDTO[];
  mensaje!: string;
}