package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

@Data
public class ApiCatalogModuleGroupDto {
    private String groupId;
    private String groupTitle;
    private String parentGroupId;
    private String groupCode;
    private ApiCatalogModuleDto[] modules;
    private ApiCatalogModuleGroupDto[] moduleSubgroups;
}
