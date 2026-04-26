//This class calculates risk and severity for each Checkov finding
//Only used when the original JSON either lacks severity or to add a risk score

public class RiskCalculator {

    //Determines the severity through text based on the issue type
    public static String calculateSeverity(String checkName, String resource) {
        //changes them to lowercase to allow for more simple matching
        String name = checkName.toLowerCase();
        String res = resource.toLowerCase();

        // Heuristic rules based on keywords in check name or resource
        if (name.contains("public") || name.contains("0.0.0.0")) {
            return "HIGH";
        } else if (name.contains("encryption")) {
            return "HIGH";
        } else if (res.contains("iam") || name.contains("policy")) {
            return "HIGH";
        } else if (name.contains("logging")) {
            return "MEDIUM";
        } else {
            //everything else thats not above is a low priority
            return "LOW";
        }
    }

    //Assigns numerical risk for easier sorting/prioritization
    public static int calculateRiskScore(String checkName, String resource) {
        String name = checkName.toLowerCase();

        //Just like above but returns numbers
        if (name.contains("public") || name.contains("0.0.0.0")) {
            //Highest priority
            return 9;
        } else if (name.contains("encryption")) {
            return 7;
        } else if (name.contains("policy")) {
            return 8;
        } else if (name.contains("logging")) {
            return 5;
        } else {
            //Lowest priority
            return 3;
        }
    }
}
