package ee.ria.eits.backoffice.contracts;

import lombok.Data;

@Data
public class ModuleDto {
    private String code;
    private MeasureDto[] measures;
}
