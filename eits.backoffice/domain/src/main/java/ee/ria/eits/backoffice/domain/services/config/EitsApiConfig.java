package ee.ria.eits.backoffice.domain.services.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ria.eits.backoffice.api_client.handler.EitsApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EitsApiConfig {

    @Value("${api.eits-base-url}")
    private String baseUrl;

    @Bean
    public EitsApiClient eitsApiClient(ObjectMapper objectMapper) {
        return new EitsApiClient(baseUrl, objectMapper);
    }
}
