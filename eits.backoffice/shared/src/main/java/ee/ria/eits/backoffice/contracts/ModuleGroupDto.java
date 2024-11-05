package ee.ria.eits.backoffice.contracts;

import lombok.Data;

@Data
public class ModuleGroupDto {
    private String groupCode;
    private ModuleDto[] modules;
    private ModuleGroupDto[] subGroups;
}
