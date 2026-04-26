import java.util.List;

// The class that decides which parser is actually being used
public class Dispatcher {

    //Chooses which parser to run based on tool name
    public static List<Finding> parse(String tool, String filePath) {

        switch (tool.toLowerCase()) {

            case "checkov":
                return CheckovParser.parse(filePath);

            case "terrascan":
                return TerrascanParser.parse(filePath);

            default:
                throw new IllegalArgumentException("Unknown tool: " + tool);
        }
    }
}
