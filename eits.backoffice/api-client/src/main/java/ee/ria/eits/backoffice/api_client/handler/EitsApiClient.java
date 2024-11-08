package ee.ria.eits.backoffice.api_client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ria.eits.backoffice.api_client.exceptions.ApiEntryNotFoundException;
import ee.ria.eits.backoffice.api_client.exceptions.ApiIllegalArgumentException;
import ee.ria.eits.backoffice.api_client.model.ApiCatalogDto;
import ee.ria.eits.backoffice.api_client.model.ApiCatalogVersionsResponse;
import ee.ria.eits.backoffice.api_client.model.ApiEitsErrorDto;
import ee.ria.eits.backoffice.api_client.model.ApiHealthCheckDto;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

import java.io.IOException;

public class EitsApiClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public EitsApiClient(String baseUrl, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restClient = RestClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    public ApiHealthCheckDto getHealthStatus() throws ApiIllegalArgumentException {
        return this.restClient
                .get()
                .uri("healthz")
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, this::handleBadRequest)
                .toEntity(ApiHealthCheckDto.class)
                .getBody();
    }

    public ApiCatalogDto getCatalog(String version)
            throws ApiIllegalArgumentException, ApiEntryNotFoundException {
        return this.restClient
                .get()
                .uri("api/2/catalog/{version}", version)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, this::handleBadRequest)
                .onStatus(HttpStatus.NOT_FOUND::equals, EitsApiClient::handleNotFound)
                .toEntity(ApiCatalogDto.class)
                .getBody();
    }

    public JsonNode getArticles(String version)
            throws ApiIllegalArgumentException, ApiEntryNotFoundException {
        return this.restClient
                .get()
                .uri("api/2/article/{version}",version)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, this::handleBadRequest)
                .onStatus(HttpStatus.NOT_FOUND::equals, EitsApiClient::handleNotFound)
                .toEntity(JsonNode.class)
                .getBody();
    }

    public ApiCatalogVersionsResponse getCatalogVersions()
            throws ApiIllegalArgumentException, ApiEntryNotFoundException {
        return this.restClient
                .get()
                .uri("api/2/catalog")
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, this::handleBadRequest)
                .onStatus(HttpStatus.NOT_FOUND::equals, EitsApiClient::handleNotFound)
                .toEntity(ApiCatalogVersionsResponse.class)
                .getBody();
    }

    private void handleBadRequest(HttpRequest request, ClientHttpResponse response)
            throws IOException, ApiIllegalArgumentException {
        ApiEitsErrorDto errorDto = objectMapper.readValue(response.getBody(), ApiEitsErrorDto.class);
        throw new ApiIllegalArgumentException(errorDto.getMessages());
    }


    private static void handleNotFound(HttpRequest request, ClientHttpResponse response) {
        throw new ApiEntryNotFoundException();
    }
}
