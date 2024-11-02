package ee.ria.eits.backoffice.api_client.handler;


import ee.ria.eits.backoffice.api_client.model.HealthCheckResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class EitsApiClient {
    private final RestClient restClient;

    public EitsApiClient(String baseUrl) {
        this.restClient = RestClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    public HealthCheckResponse getHealthStatus() {
        ResponseEntity<HealthCheckResponse> result = this.restClient
                .get()
                .uri("healthz")
                .retrieve()
                .toEntity(HealthCheckResponse.class);

        return result.getBody();
    }
}
