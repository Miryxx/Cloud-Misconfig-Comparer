import java.util.List;

//This is the main class that runs the whole program and coordinates all components
//It executes both Checkov and Terrascan separately so results can be compared

public class Main {

    public static void main(String[] args) {

        //File paths for each tools JSON output
        String checkovFilePath = "resultsCheckov.json";
        String terrascanFilePath = "terrascan-output.json";

        //Parses Checkov JSON using dispatcher
        List<Finding> checkovFindings =
                Dispatcher.parse("checkov", checkovFilePath);

        //Parses Terrascan JSON using dispatcher
        List<Finding> terrascanFindings =
                Dispatcher.parse("terrascan", terrascanFilePath);

        //Prints Checkov results separately for comparison
        System.out.println("\n~~~~ CHECKOV FINDINGS ~~~~\n");
        printFindings(checkovFindings);

        //Prints Terrascan results separately for comparison
        System.out.println("\n~~~~ TERRASCAN FINDINGS ~~~~\n");
        printFindings(terrascanFindings);
    }

    //Helper method that prints findings + summary statistics
    //This allows both tools to use identical reporting logic
    public static void printFindings(List<Finding> findings) {

        //Sort findings by risk score (highest severity first)
        findings.sort((a, b) -> Integer.compare(b.riskScore, a.riskScore));

        //Print each finding using its toString() method
        for (Finding f : findings) {
            System.out.println(f);
        }

        //Initialize severity counters
        int high = 0, medium = 0, low = 0, unknown = 0;

        //Count severity distribution
        for (Finding f : findings) {

            String severity = f.getSeverity();

            if (severity == null) {
                unknown++;
            } else {
                switch (severity.toUpperCase()) {
                    case "HIGH":
                        high++;
                        break;
                    case "MEDIUM":
                        medium++;
                        break;
                    case "LOW":
                        low++;
                        break;
                    default:
                        unknown++;
                }
            }
        }

        //Print summary statistics
        System.out.println("\n~~~~ SUMMARY ~~~~");
        System.out.println("Total Issues: " + findings.size());
        System.out.println("HIGH: " + high);
        System.out.println("MEDIUM: " + medium);
        System.out.println("LOW: " + low);
        System.out.println("UNKNOWN: " + unknown);
    }
}