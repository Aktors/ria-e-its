package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ApiFaultResponse {
    private List<String> messages;
    private ZonedDateTime timestamp;
    private String path;
}
