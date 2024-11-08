package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiEitsErrorDto {
    private String[] messages;
    private Date timestamp;
    private String path;
}
