package ee.ria.eits.backoffice.domain.services;

import ee.ria.eits.backoffice.contracts.CatalogDto;
import ee.ria.eits.backoffice.contracts.MeasureDto;
import ee.ria.eits.backoffice.contracts.ModuleGroupDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MeasureRepository {

    private final CatalogService catalogService;
    private final Map<String, MeasureDto[]> cache;

    public MeasureRepository(CatalogService catalogService) {
        this.catalogService = catalogService;
        this.cache = new HashMap<>();
    }

    public MeasureDto[] getMeasures(String version){
        cache.computeIfAbsent(version, key -> getCatalogMeasures(catalogService.getCatalog(version)));
        return cache.get(version);
    }

    public void clearCache(){
        this.cache.clear();
    }

    private static MeasureDto[] getCatalogMeasures(CatalogDto catalog) {
        ArrayList<MeasureDto> measures = new ArrayList<>();
        Arrays.stream(catalog.getModuleGroups()).forEach(group -> measures.addAll(getModuleGroupMeasures(group)));
        return measures.toArray(MeasureDto[]::new);
    }

    private static List<MeasureDto> getModuleGroupMeasures(ModuleGroupDto group) {
        ArrayList<MeasureDto> measures = new ArrayList<>();

        Arrays.stream(group.getModules()).forEach(module -> measures.addAll(List.of(module.getMeasures())));
        Arrays.stream(group.getSubGroups()).forEach(subGroup -> measures.addAll(getModuleGroupMeasures(subGroup)));

        return measures;
    }
}
