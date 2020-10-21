package com.publicissapient.anoroc.executors.anoroc;

import com.publicissapient.anoroc.AnorocReporting;
import com.publicissapient.anoroc.dto.Report;
import com.publicissapient.anoroc.executors.Executor;
import com.publicissapient.anoroc.generator.HtmlReportGenerator;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.report.FeatureReport;
import com.publicissapient.anoroc.report.RPReportGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
@Slf4j
public class AnorocExecutor implements Executor {

    public static final String REPORT_STATIC_LOCATION = "static/";

    @Value("${anoroc.runs.report.path}")
    private String reportPath;

    @Autowired
    private AnorocExecutionEngine executionEngine;

    public void run(RunPayload run, Optional<RPReportGenerator> rpReportGenerator) {
        try {
            log.info("Received run command for feature: " + run.getName());
            Date startTime = new Date();
            String featureUUID = UUID.randomUUID().toString();
            if(rpReportGenerator.isPresent()) rpReportGenerator.get().startFeatureReport(run.getName(), featureUUID);
            List<Feature> featuresExecuted = executionEngine.executeFeature(run);
            checkStatusAndUpdate(run, featuresExecuted);
            Date endTime = new Date();
            generateReport(run, startTime, featuresExecuted, endTime,rpReportGenerator, featureUUID);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            run.setErrorDescription(e.getMessage());
            run.setStatus(RunStatus.ERROR);
        }
    }

    private Report generateReport(RunPayload run, Date startTime, List<Feature> featuresExecuted, Date endTime, Optional<RPReportGenerator> rpReportGenerator, String uuid) {
        displayExecutionTime(startTime, endTime);
        updateToReportportal(run, featuresExecuted, rpReportGenerator, uuid);
        AnorocReporting anorocReporting = new AnorocReporting();
        return generateJSONReport(featuresExecuted, run.getRunId(), anorocReporting);
    }

    private void updateToReportportal(RunPayload run, List<Feature> featuresExecuted, Optional<RPReportGenerator> rpReportGenerator, String uuid) {
            List<FeatureReport> featureReports = featuresExecuted.stream()
                    .map(feature -> FeatureReport.build(feature.getName()).buildScenarioOutlines(feature))
                    .collect(Collectors.toList());
        if(rpReportGenerator.isPresent()) rpReportGenerator.get().finishFeatureReport(featureReports.get(0), uuid);
    }

    private void updateRunStatus(Report report, RunPayload run) throws IOException {
        run.setReportPath(run.getRunId() + HtmlReportGenerator.HTML_REPORT_FILE_PATH);
    }

    private void checkStatusAndUpdate(RunPayload run, List<Feature> featuresExecuted) {
        if (isRunFailure(featuresExecuted)) {
            run.setStatus(RunStatus.FAILURE);
        } else {
            run.setStatus(RunStatus.SUCCESS);
        }
    }

    private boolean isRunFailure(List<Feature> featuresExecuted) {
        return featuresExecuted
                .stream().anyMatch(feature -> feature.getScenarioOutlines()
                        .stream().anyMatch(scenarioOutline -> scenarioOutline.getStepDefinitions()
                                .stream().anyMatch(stepDefinition -> stepDefinition.getResult().getStatus() != Status.PASSED)));
    }

    private void displayExecutionTime(Date startTime, Date endTime) {
        long nanoTime = endTime.getTime() - startTime.getTime();
        long diffSeconds = nanoTime / 1000 % 60;
        long diffMinutes = nanoTime / (60 * 1000) % 60;
        long diffHours = nanoTime / (60 * 60 * 1000) % 24;
        log.info("Total execution time " + diffHours + "h:" + diffMinutes + "m:" + diffSeconds + "s");
    }

    private Report generateJSONReport(List<Feature> featureListForReport, Long runId, AnorocReporting anorocReporting) {
        Report report = new Report(featureListForReport);
        report.setReportDirectory(reportPath+ File.separator+ runId);
        report = anorocReporting.generateJSONReport(report);
        return report;
    }
}




