package ee.ria.eits.backoffice.api_client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import ee.ria.eits.backoffice.api_client.model.ApiCatalogDto;
import ee.ria.eits.backoffice.api_client.model.ApiCatalogVersionsResponse;
import ee.ria.eits.backoffice.api_client.model.ApiHealthCheckDto;
import ee.ria.eits.backoffice.api_client.model.ApiVersionDto;
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

    public ApiHealthCheckDto getHealthStatus() {
        ResponseEntity<ApiHealthCheckDto> result = this.restClient
                .get()
                .uri("healthz")
                .retrieve()
                .toEntity(ApiHealthCheckDto.class);

        return result.getBody();
    }

    public ApiCatalogDto getCatalog(String version) {
        ResponseEntity<ApiCatalogDto> result = this.restClient
                .get()
                .uri("api/2/catalog/{version}", version)
                .retrieve()
                .toEntity(ApiCatalogDto.class);

        return result.getBody();
    }

    public JsonNode getArticles(String version) {
        ResponseEntity<JsonNode> result = this.restClient
                .get()
                .uri("api/2/article/{version}",version)
                .retrieve()
                .toEntity(JsonNode.class);

        return result.getBody();
    }

    public ApiCatalogVersionsResponse getCatalogVersions(){
        ResponseEntity<ApiCatalogVersionsResponse> result = this.restClient
                .get()
                .uri("api/2/catalog")
                .retrieve()
                .toEntity(ApiCatalogVersionsResponse.class);

        return result.getBody();
    }
}
