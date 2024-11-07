package ee.ria.eits.backoffice.controllers;

import ee.ria.eits.backoffice.contracts.MeasureDto;
import ee.ria.eits.backoffice.domain.services.CatalogService;
import ee.ria.eits.backoffice.domain.services.MeasureRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/measures")
public class MeasuresController {
    private final MeasureRepository measureRepository;
    private final CatalogService catalogService;

    public MeasuresController(
            MeasureRepository measureRepository,
            CatalogService catalogService) {
        this.measureRepository = measureRepository;
        this.catalogService = catalogService;
    }

    @GetMapping("/{version}")
    public MeasureDto[] getCatalog(@PathVariable String version) {
        return measureRepository.getMeasures(version);
    }

    @GetMapping("/versions")
    public String[] getVersions() {
        return this.catalogService.getVersions();
    }
}
