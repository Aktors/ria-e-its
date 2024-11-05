package ee.ria.eits.backoffice.domain.helpers;

import enums.MeasureType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ee.ria.eits.backoffice.contracts.MeasureDto;
import ee.ria.eits.backoffice.contracts.ModuleDto;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static ee.ria.eits.backoffice.domain.utils.NodeParserUtil.findNodeByProperty;
import static ee.ria.eits.backoffice.domain.utils.NodeParserUtil.getChildNodes;


/**
 * Contains helper methods to parse Article JsonNode result
 */
public class ArticleNodeParser {

    private static final String MEASURE_GROUP = "3 Meetmed";
    private static final String MAIN_MEASURE = "3.2 Põhimeetmed";
    private static final String STANDARD_MEASURE = "3.3 Standardmeetmed";
    private static final String HIGH_MEASURE = "3.4 Kõrgmeetmed";

    private static final String FIELD_TITLE = "title";
    private static final String FIELD_CHILD = "child_objects";
    private static final String FIELD_PAGE = "parentPage";

    private ArticleNodeParser() {}

    public static void parseMeasures(ModuleDto module, JsonNode moduleData) {
        JsonNode measuresNode = findNodeByProperty(moduleData, FIELD_TITLE, MEASURE_GROUP, FIELD_CHILD);

        ArrayList<MeasureDto> measures = new ArrayList<>();
        Optional.of(findNodeByProperty(measuresNode, FIELD_TITLE, MAIN_MEASURE, FIELD_CHILD))
                .ifPresent(groupNode -> appendMeasureGroup(measures,groupNode, MeasureType.MAIN));
        Optional.of(findNodeByProperty(measuresNode, FIELD_TITLE, STANDARD_MEASURE, FIELD_CHILD))
                .ifPresent(groupNode -> appendMeasureGroup(measures,groupNode, MeasureType.STANDARD));
        Optional.of(findNodeByProperty(measuresNode, FIELD_TITLE, HIGH_MEASURE, FIELD_CHILD))
                .ifPresent(groupNode -> appendMeasureGroup(measures,groupNode, MeasureType.HIGH));

        module.setMeasures(measures.toArray(new MeasureDto[0]));
    }

    public static void parseArticles(JsonNode currentNode, Map<String, ModuleDto> modules) {
        if (currentNode.isArray()) {
            for (JsonNode childNode : currentNode)
                parseArticles(childNode, modules);
        }else {
            if(currentNode.has(FIELD_PAGE) && currentNode.get(FIELD_PAGE).asBoolean()){
                String[] parts = parseTitle(currentNode.get(FIELD_TITLE).asText(),':');
                if(parts.length == 2) {
                    String moduleCode = parts[0];
                    if(modules.containsKey(moduleCode)) {
                        parseMeasures(modules.get(moduleCode),currentNode);
                    }
                }
            }

            ArrayNode childNodes = getChildNodes(currentNode,FIELD_CHILD);
            if(childNodes != null)
                parseArticles(childNodes,modules);
        }
    }


    private static void appendMeasureGroup(ArrayList<MeasureDto> measures, JsonNode measureGroup, MeasureType measureType) {
        ArrayNode children = getChildNodes(measureGroup, FIELD_CHILD);
        if (children != null) {
            for (JsonNode measureNode : children) {
                measures.add(parseMeasure(measureNode, measureType));
            }
        }
    }

    private static MeasureDto parseMeasure(JsonNode measureNode, MeasureType type){
        String title = measureNode.get(FIELD_TITLE).asText();
        var parts = parseTitle(title,' ');

        var measure = new MeasureDto();
        measure.setCode(parts[0]);
        measure.setTitle(parts[1]);
        measure.setType(type);
        measure.setContent(measureNode.get("content").asText());
        return measure;
    }


    private static String[] parseTitle(String title, char separator) {
        int firstSpaceIndex = title.indexOf(separator);
        if (firstSpaceIndex == -1) {
            return new String[] { title };
        }

        String code = title.substring(0, firstSpaceIndex);
        String name = title.substring(firstSpaceIndex + 1);

        return new String[] { code, name };
    }

}
