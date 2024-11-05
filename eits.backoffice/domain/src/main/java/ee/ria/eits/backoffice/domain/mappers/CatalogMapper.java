package ee.ria.eits.backoffice.domain.mappers;

import ee.ria.eits.backoffice.api_client.model.ApiCatalogDto;
import ee.ria.eits.backoffice.api_client.model.ApiCatalogModuleDto;
import ee.ria.eits.backoffice.api_client.model.ApiCatalogModuleGroupDto;
import ee.ria.eits.backoffice.contracts.CatalogDto;
import ee.ria.eits.backoffice.contracts.ModuleDto;
import ee.ria.eits.backoffice.contracts.ModuleGroupDto;

import java.util.Arrays;
import java.util.Optional;

/**
 * Contains helper methods to convert API call results to domain model.
 */
public class CatalogMapper {

    private CatalogMapper() {}

    public static CatalogDto toCatalogDto(ApiCatalogDto apiCatalog) {
        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setVersion(apiCatalog.getVersion());
        catalogDto.setLang(apiCatalog.getLang());

        Optional.of(apiCatalog.getModuleGroups())
                .ifPresent(groups -> catalogDto.setModuleGroups(
                        Arrays.stream(apiCatalog.getModuleGroups())
                            .map(CatalogMapper::toModuleGroupDto)
                            .toArray(ModuleGroupDto[]::new))
                );

        return catalogDto;
    }

    public static ModuleGroupDto toModuleGroupDto(ApiCatalogModuleGroupDto moduleGroupDto) {
        ModuleGroupDto moduleGroup = new ModuleGroupDto();
        moduleGroup.setGroupCode(moduleGroupDto.getGroupCode());

        Optional.of(moduleGroupDto.getModules())
               .ifPresent(modules -> moduleGroup.setModules(
                       Arrays.stream(modules)
                           .map(CatalogMapper::toModuleDto)
                           .toArray(ModuleDto[]::new)
               ));

        Optional.of(moduleGroupDto.getModuleSubgroups())
                .ifPresent(groups -> moduleGroup.setSubGroups(
                        Arrays.stream(groups)
                            .map(CatalogMapper::toModuleGroupDto)
                            .toArray(ModuleGroupDto[]::new))
                );

        return moduleGroup;
    }

    public static ModuleDto toModuleDto(ApiCatalogModuleDto moduleDto) {
        ModuleDto module = new ModuleDto();
        module.setCode(moduleDto.getModuleCode());
        return module;
    }
}
