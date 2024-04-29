package fr.abes.theses.export.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI().servers(Arrays.asList(new Server().url("https://theses.fr")))
                .info(new Info().title("API exportation des métadonnées de theses.fr")
                        .description("Cette API permet d’exporter les métadonnées descriptives d’une thèse soutenue ou d’une thèse en préparation aux formats XML ou RDF ainsi que la référence bibliographique d’une thèse soutenue ou d’une thèse en préparation aux formats RIS et BibTeX.")
                        .version("1.0"));
    }

}
