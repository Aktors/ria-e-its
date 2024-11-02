package ee.ria.eits.backoffice.controllers;

import ee.ria.eits.backoffice.contracts.DiagnosticsData;
import ee.ria.eits.backoffice.domain.services.DiagnosticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
public class SystemController {
    private final DiagnosticsService diagnosticsService;
    public SystemController(DiagnosticsService diagnosticsServiced) {
        this.diagnosticsService = diagnosticsServiced;
    }
    @GetMapping("/heartbeat")
    public String getHeartbeat() {
        return "ok";
    }

    @GetMapping("/diagnostics")
    public DiagnosticsData getDiagnosticsData() {
        return diagnosticsService.getDiagnostics();
    }
}
