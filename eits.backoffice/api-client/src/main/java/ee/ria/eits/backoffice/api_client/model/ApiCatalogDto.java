package ee.ria.eits.backoffice.api_client.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiCatalogDto {
    private String version;
    private String lang;
    private Date validFrom;
    private Date validTo;
    private String id;
    private ApiCatalogModuleGroupDto[] moduleGroups;
}
