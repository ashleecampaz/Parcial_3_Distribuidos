package com.edu.unicauca.servicio_financiera.fachadaServices.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.edu.unicauca.servicio_financiera.capaAccesoADatos.models.Deuda;
import com.edu.unicauca.servicio_financiera.capaAccesoADatos.repositories.DeudaRepository;
import com.edu.unicauca.servicio_financiera.fachadaServices.DTO.DTORespuesta;

@Service
public class FinancieraService {

    private final DeudaRepository deudaRepository;
    private final ModelMapper modelMapper;

    public FinancieraService(DeudaRepository deudaRepository, ModelMapper modelMapper) {
        this.deudaRepository = deudaRepository;
        this.modelMapper = modelMapper;
    }


    public List<DTORespuesta> consultarDeudasPendientes(Integer codigoEstudiante) {
        List<Deuda> deudas = deudaRepository.findDeudasPendientesByCodigoEstudiante(codigoEstudiante);

        return deudas.stream()
                .map(deuda -> modelMapper.map(deuda, DTORespuesta.class))
                .collect(Collectors.toList());
    }

    public boolean eliminarPendientes(Integer codigoEstudiante) {
        return deudaRepository.eliminarDeudasPendientes(codigoEstudiante);
    }

}
