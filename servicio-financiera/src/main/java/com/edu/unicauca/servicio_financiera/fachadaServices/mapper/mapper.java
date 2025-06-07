package com.edu.unicauca.servicio_financiera.fachadaServices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class mapper {
    @Bean
    public ModelMapper crearMapeador() {
        System.out.println("Ejecución del método crearMapeador");
        return new ModelMapper();
    }
    


}
