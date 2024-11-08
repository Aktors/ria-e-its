package ee.ria.eits.backoffice.domain.services;

import com.fasterxml.jackson.databind.JsonNode;
import ee.ria.eits.backoffice.api_client.handler.EitsApiClient;
import ee.ria.eits.backoffice.api_client.model.ApiVersionDto;
import ee.ria.eits.backoffice.contracts.CatalogDto;
import ee.ria.eits.backoffice.contracts.ModuleDto;
import ee.ria.eits.backoffice.contracts.ModuleGroupDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ee.ria.eits.backoffice.domain.helpers.ArticleNodeParser.parseArticles;
import static ee.ria.eits.backoffice.domain.mappers.CatalogMapper.toCatalogDto;

@Service
public class CatalogService {
    private final EitsApiClient eitsApiClient;

    public CatalogService(EitsApiClient eitsApiClient) {
        this.eitsApiClient = eitsApiClient;
    }

    public CatalogDto getCatalog(String version) {
        CatalogDto catalog = toCatalogDto(eitsApiClient.getCatalog(version));

        Map<String, ModuleDto> modules = new HashMap<>();
        Arrays.stream(catalog.getModuleGroups()).forEach(module -> toModulesMap(modules, module));

        JsonNode articles = eitsApiClient.getArticles(version);
        parseArticles(articles, modules);

        return catalog;
    }

    public String[] getVersions(){
        var catalogVersions = this.eitsApiClient.getCatalogVersions();

        return Arrays.stream(catalogVersions.getVersions())
                .map(ApiVersionDto::getVersion)
                .toArray(String[]::new);
    }

    private void toModulesMap(Map<String, ModuleDto> catalog, ModuleGroupDto moduleGroupDto) {
        Optional.ofNullable(moduleGroupDto.getModules())
                .ifPresent(modules -> Arrays.stream(modules)
                    .forEach(module -> catalog.putIfAbsent(module.getCode(),module)));

        Optional.ofNullable(moduleGroupDto.getSubGroups()).ifPresent(
                modules -> Arrays.stream(modules)
                        .forEach(module -> toModulesMap(catalog, module)));
    }
}
