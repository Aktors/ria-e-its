package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

@Data
public class ApiCatalogModuleDto {
    private String moduleId;
    private String groupId;
    private String moduleTitle;
    private String link;
    private String moduleCode;
    private ApiMeasureGroupDto[] measureDetails;
}
