// This class represents a single security issue (or finding) from Checkov or Terrascan
// Each object of this class will store information about one failed check
// The below references was used to help with creation of this class:
// https://www.baeldung.com/jackson-object-mapper-tutorial
// https://www.javaguides.net/2019/04/jackson-convert-java-object-tofrom-json-example.html

public class Finding {

    // The unique ID for the security check to identify it
     public String checkId;
     // A description of the issue
     public String checkName;
     // Risk assessment level (HIGH, MEDIUM, LOW, UNKNOWN)
     public String severity;
     // Displays which cloud resource was affected
     public String resource;
     public String name;
     //The score for sorting/ranking
     public int riskScore;

     // Just a basic constructor for making the object
     public Finding(String checkId, String checkName, String severity, String resource, int riskScore) {
         this.checkId = checkId;
         this.checkName = checkName;
         this.severity = severity;
         this.resource = resource;
     }

     //Setter
     public void setRiskScore(int score) {
         this.riskScore = score;
     }

     @Override
    public String toString() {
         return "Check ID: " + checkId +
                 "\nIssue: " + checkName +
                 "\nSeverity: " + severity +
                 "\nRisk Score: " + riskScore +
                 "\nResource: " + resource +
                 "\n-----------------------------";
     }

    public String getSeverity() {
        return severity;
    }

    public String getName() {
        return name;
    }

    public String getResource() {
        return resource;
    }
}
