package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiVersionDto {
    private String version;
    private Date validFrom;
    private Date validTo;
}
