package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

@Data
public class ApiMeasureGroupDto {
    private String groupId;
    private String groupTitle;
    private String groupCode;
    private ApiMeasureDetailDto[] measures;
}
