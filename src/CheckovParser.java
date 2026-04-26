import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// This class is the one that reads the JSON file created by Checkov
// It also converts it into a list of Finding objects
// The below references were used to help with the creation of this class:
// https://www.javadoc.io/static/com.fasterxml.jackson.core/jackson-databind/2.10.0/com/fasterxml/jackson/databind/ObjectMapper.html
// https://jenkov.com/tutorials/java-json/jackson-objectmapper.html

public class CheckovParser {

    //Method reads JSON file and returns it as a list of Findings
    public static List<Finding> parse(String filePath) {
        //Creates a new list to store all findings
        List<Finding> findings = new ArrayList<>();

        try {
            //This is the main part that reads the JSON file
            ObjectMapper mapper = new ObjectMapper();
            //While reading the JSON file, it puts it into a tree
            JsonNode rootArray = mapper.readTree(new File(filePath));

            //Checkovs JSON file starts with an Array so we need to loop through it
            for (JsonNode root : rootArray) {
                //Gets the results section
                JsonNode results = root.get("results");
                if (results == null) {
                    continue;
                }

                JsonNode failedChecks = results.get("failed_checks");
                if (failedChecks == null) {
                    continue;
                }

                for (JsonNode node : failedChecks) {
                    JsonNode checkResult = node.get("check_result");

                    if (checkResult != null && checkResult.get("result") != null && !checkResult.get("result").asText().equals("FAILED")) {
                        continue;
                    }

                    String id = node.get("check_id").asText();
                    String name = node.get("check_name").asText();
                    String resource = node.has("resource") ? node.get("resource").asText() : "N/A";

                    String severity = RiskCalculator.calculateSeverity(name, resource);
                    int riskScore = RiskCalculator.calculateRiskScore(name, resource);

                    findings.add(new Finding(id, name, severity, resource, riskScore));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return findings;
    }
}