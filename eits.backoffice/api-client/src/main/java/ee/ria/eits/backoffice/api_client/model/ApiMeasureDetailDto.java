package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

@Data
public class ApiMeasureDetailDto {
    private String measureId;
    private String measureTitle;
    private String body;
    private String[] assignees;
    private String[] securityCodes;
    private String measureCode;
}
