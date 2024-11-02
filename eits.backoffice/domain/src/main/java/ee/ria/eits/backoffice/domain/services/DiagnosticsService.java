package ee.ria.eits.backoffice.domain.services;

import ee.ria.eits.backoffice.api_client.handler.EitsApiClient;
import ee.ria.eits.backoffice.api_client.model.HealthCheckResponse;
import ee.ria.eits.backoffice.contracts.DependentSystem;
import ee.ria.eits.backoffice.contracts.DiagnosticsData;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticsService {
    private final EitsApiClient eitsApiClient;
    public DiagnosticsService(EitsApiClient eitsApiClient) {
        this.eitsApiClient = eitsApiClient;
    }

    public DiagnosticsData getDiagnostics() {
        return new DiagnosticsData()
                .addDependentSystem(this.getEitsStatus())
                // Add more dependent systems here in the future
                ;
    }

    private DependentSystem getEitsStatus(){
        DependentSystem eits = new DependentSystem();
        eits.setSystemName("E-ITS");
        try {
            HealthCheckResponse eitsHealth = eitsApiClient.getHealthStatus();
            eits.setStatusText("ok");
        } catch (Exception e){
            eits.setStatusText("error");
        }
        return eits;
    }
}
