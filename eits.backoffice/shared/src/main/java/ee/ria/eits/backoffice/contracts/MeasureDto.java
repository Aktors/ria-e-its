package ee.ria.eits.backoffice.contracts;

import enums.MeasureType;
import lombok.Data;

@Data
public class MeasureDto {
    private String title;
    private MeasureType type;
    private String code;
    private String content;
}
