package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;
@Data
public class HealthCheckResponse {
    private String appName;
    private String version;
    private long packagingTime;
    private long appStartTime;
    private long serverTime;
}
