package co.edu.unicauca.orquestador.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient metodoAEjecutar(WebClient.Builder builder) {
        return builder.build();
    }
    
}
