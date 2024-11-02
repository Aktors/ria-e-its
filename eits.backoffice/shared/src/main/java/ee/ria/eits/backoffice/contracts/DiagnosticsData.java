package ee.ria.eits.backoffice.contracts;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DiagnosticsData {

    public DiagnosticsData() {
        dependentSystems = new ArrayList<>();
    }

    private List<DependentSystem> dependentSystems;

    public DiagnosticsData addDependentSystem(DependentSystem dependentSystem) {
        this.dependentSystems.add(dependentSystem);
        return this;
    }
}
