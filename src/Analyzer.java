import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class analyzes the findings and produces a summary of the statistics
// The below references were used to help with the creation of this class:
// https://codingtechroom.com/tutorial/java-mastering-java-jackson-working-with-jsonnode-collections

public class Analyzer {

    // This method takes a list of findings and prints a summary
    public static void analyze (List<Finding> findings) {
        // A map to store count of each severity type
        Map<String, Integer> severityCount = new HashMap<>();

        // Loops through each finding
        for (Finding f : findings) {
            // Counts how many times each severity appears
            severityCount.put (f.severity, severityCount.getOrDefault(f.severity, 0) + 1);
        }

        // Prints a header for the summary for organization and aesthetics
        System.out.println("~~~ Misconfiguration Summary ~~~");

        // Prints each severity count
        for (String severity : severityCount.keySet()) {
            System.out.println(severity + ": " + severityCount.get(severity));
        }

        // Prints the total number of issues
        System.out.println("Total Issues: " + findings.size());
    }
}
