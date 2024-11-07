package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

@Data
public class ApiCatalogVersionsResponse {
    private ApiVersionDto[] versions;
}
