package com.publicissapient.anoroc;


import com.publicissapient.anoroc.dto.Report;
import com.publicissapient.anoroc.factory.ReportGeneratorFactory;

public class AnorocReporting {

    public Report generateReport(Report report) {
        return ReportGeneratorFactory.getHtmlReportGenerator(
                ReportGeneratorFactory
                        .getJsonReportGenerator(report)
                        .generateReport()
        ).generateReport();
    }
    public Report generateJSONReport(Report report) {
        return ReportGeneratorFactory
                        .getJsonReportGenerator(report)
                        .generateReport();
    }

    public Report generateHTMLReport(Report report) {
        return ReportGeneratorFactory
                .getHtmlReportGenerator(report)
                .generateReport();
    }
}
