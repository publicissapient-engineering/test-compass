package com.publicissapient.anoroc.generator;

import com.publicissapient.anoroc.dto.Report;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;

import java.io.File;
import java.util.Collections;


public class HtmlReportGenerator extends ReportGenerator {

    public static final String HTML_REPORT_FILE_PATH = "/cucumber-html-reports/overview-features.html";

    public HtmlReportGenerator(Report report) {
        super(report);
    }

    @Override
    public Report generateReport() {

        Configuration config = getConfiguration(this.report);
        ReportBuilder reportBuilder = new ReportBuilder(this.report.getJsonFiles(), config);
        Reportable reportable = reportBuilder.generateReports();
        this.report.setHtmlFiles(Collections.singletonList(config.getReportDirectory().getAbsolutePath() + HTML_REPORT_FILE_PATH));
        return this.report;
    }

    private Configuration getConfiguration(Report report) {

        Configuration configuration = new Configuration(new File(report.getReportDirectory()), report.getProjectName());
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);

        return configuration;
    }
}
