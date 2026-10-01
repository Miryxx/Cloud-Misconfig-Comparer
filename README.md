# Cloud-Misconfig-Comparer

A Java prototype I built for my Information Assurance class at UNF. It takes the JSON output from two IaC security scanners, Checkov and Terrascan, and converts it into one common format so the results can be compared directly.

This is an academic prototype. Some paths are hardcoded and it isn't production-ready. The paper I wrote about it covers the design and results in more detail: [the paper](InfoAssuranceFinalPaper.pdf)

## Why I built it

Checkov and Terrascan both scan Terraform files for cloud misconfigurations like public S3 buckets or open security groups, but they report results differently. The JSON structure, rule names, and severity labels don't line up, so if you run both it's hard to tell how their findings compare or what to fix first. I wanted to see if I could standardize the output without losing track of which tool found what.

## How it works

The program starts with a Dispatcher class that looks at a JSON file and figures out whether it came from Checkov or Terrascan, then hands it to the matching parser. Each parser turns the tool's findings into a common Finding object that stores the rule, severity, affected resource, description, and source tool. A RiskNormalizer then maps severities onto one scale (low, medium, high as 1, 2, 3) and fills in a basic value when a tool doesn't give one. Checkov's severity data is limited without a platform account, and Terrascan gives severity labels but no risk score, so this step was needed to compare them at all.

The last step prints a summary with the number of findings per tool and the details of each, sorted by normalized severity. I chose not to merge or deduplicate findings across tools. I wanted to see where the tools differ, so each tool's results stay separate.

Sample output:

    Check ID: AC_AWS_0231
    Issue: unrestrictedIngressAccess
    Severity: HIGH
    Risk Score: 0
    Resource: bad_sg
    ~~~~ SUMMARY ~~~~
    Total Issues: 4
    HIGH: 3
    MEDIUM: 1
    LOW: 0
    UNKNOWN: 0

## What I found

I tested it on 15 Terraform files with misconfigurations I added on purpose. The Dispatcher sent every file to the right parser, and everything ran through the full pipeline without errors. Checkov averaged around 7 findings per file and Terrascan around 4, but the bigger difference was what they looked at. Checkov mostly flagged S3 issues (public access, encryption, logging, lifecycle rules), while Terrascan flagged EC2 metadata settings, security group ingress rules, and storage versioning. Neither tool covered everything, which is the main reason to run more than one.

## Limitations

The test set is small and artificial, so I can't say how this would do on real production configs. The risk scoring is simple and only meant to fill gaps. It only supports Checkov and Terrascan, and the hardcoding means it needs some changes to run on a different setup.

## Running it

This repo is here for reference. The source is in `src/`, and the paper covers the design and results.

## What's next

If I come back to this, I'd add support for tfsec and KICS, try a better risk scoring model, and test on larger real-world configurations.

Alexis Reedy, M.S. Cybersecurity student at the University of North Florida. [LinkedIn](https://linkedin.com/in/alexis-reedy)
