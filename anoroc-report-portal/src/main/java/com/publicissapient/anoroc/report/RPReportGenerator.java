package com.publicissapient.anoroc.report;

import com.publicissapient.anoroc.report.config.ReportGeneratorConfig;
import com.publicissapient.anoroc.model.report.FeatureReport;
import com.publicissapient.anoroc.report.reporter.AnorocRPReporter;

public class RPReportGenerator {
    private AnorocRPReporter reporter;

    public RPReportGenerator(ReportGeneratorConfig generatorConfig) throws Exception {
        this.reporter = new AnorocRPReporter(generatorConfig);
    }

    public void startReport() {
        this.reporter.startReport();
    }

    public void startFeatureReport(String featureName, String uuid) {
        this.reporter.startFeatureReport(featureName, uuid);
    }

    public void finishFeatureReport(FeatureReport result, String uuid) {
        this.reporter.finishFeatureReport(result, uuid);
    }

    public String completeReport() {
        return this.reporter.completeReport();
    }
}
