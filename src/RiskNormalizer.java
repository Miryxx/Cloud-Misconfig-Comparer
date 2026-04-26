//Makes sure all inputs have the same outputs
//Terrascan doesn't have integers for severity so just adds them to
//Help sort through them for the summary list
public class RiskNormalizer {

    public static int severityToScore(String severity) {
        if (severity == null) return 0;

        switch (severity.toUpperCase()) {
            case "CRITICAL":
                return 10;
            case "HIGH":
                return 8;
            case "MEDIUM":
                return 5;
            case "LOW":
                return 2;
            default:
                return 1;
        }
    }
}
