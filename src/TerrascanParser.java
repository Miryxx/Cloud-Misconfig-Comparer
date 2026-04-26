import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// This class is the one that reads the JSON file created by Terrascan
// It also converts it into a list of Finding objects
// The below references were used to help with the creation of this class:
// https://www.javadoc.io/static/com.fasterxml.jackson.core/jackson-databind/2.10.0/com/fasterxml/jackson/databind/ObjectMapper.html
// https://jenkov.com/tutorials/java-json/jackson-objectmapper.html

public class TerrascanParser {

    public static List<Finding> parse(String filePath) {
        List<Finding> findings = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(filePath));

            JsonNode results = root.get("results");
            if (results == null) {
                return findings;
            }

            JsonNode violations = results.get("violations");
            if (violations == null) {
                return findings;
            }

            for (JsonNode node : violations) {

                String id = node.has("rule_id") ? node.get("rule_id").asText() : "N/A";
                String name = node.has("rule_name") ? node.get("rule_name").asText() : "N/A";
                String resource = node.has("resource_name") ? node.get("resource_name").asText() : "N/A";

                String severity = node.get("severity").asText();
                int riskScore = RiskNormalizer.severityToScore(severity);

                findings.add(new Finding(id, name, severity, resource, riskScore));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return findings;
    }
}