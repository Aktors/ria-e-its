package ee.ria.eits.backoffice.contracts;

import lombok.Data;

@Data
public class CatalogDto {
    private String version;
    private String lang;
    private ModuleGroupDto[] moduleGroups;
}
