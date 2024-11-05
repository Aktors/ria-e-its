package ee.ria.eits.backoffice.domain.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class NodeParserUtil {
    /**
     * Searches for a node in the given JsonNode tree that contains a specified property with a specified value.
     *
     * @param rootNode the root JsonNode to start the search
     * @param propertyName the name of the property to match
     * @param propertyValue the value of the property to match
     * @return the JsonNode that contains the property with the specified value, or null if not found
     */
    public static JsonNode findNodeByProperty(JsonNode rootNode, String propertyName, String propertyValue, String childNodesPropertyName) {
        if (rootNode == null) {
            return null;
        }

        if (rootNode.has(propertyName) && rootNode.get(propertyName).asText().equals(propertyValue)) {
            return rootNode;
        }

        ArrayNode childNodes = getChildNodes(rootNode, childNodesPropertyName);
        if (childNodes != null) {
            for (JsonNode childNode : childNodes) {
                JsonNode result = findNodeByProperty(childNode, propertyName, propertyValue,childNodesPropertyName);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public static ArrayNode getChildNodes(JsonNode rootNode, String childNodesPropertyName) {
        if (rootNode.has(childNodesPropertyName) && rootNode.path(childNodesPropertyName).isArray())
            return (ArrayNode) rootNode.get(childNodesPropertyName);
        return null;
    }
}
