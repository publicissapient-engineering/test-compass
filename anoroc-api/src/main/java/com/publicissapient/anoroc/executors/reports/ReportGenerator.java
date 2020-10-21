package com.publicissapient.anoroc.executors.reports;

import com.intuit.karate.core.FeatureResult;
import com.publicissapient.anoroc.model.report.*;
import lombok.extern.slf4j.Slf4j;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@Slf4j
public class ReportGenerator {

    @Value("${anoroc.runs.report.path}")
    private String reportPath;

    public static final String REPORT_STATIC_PATH = "static/";

    public String initiateHtmlReportGeneration(Long runId, String projectName) {
        Configuration config = new Configuration(new File(reportPath+ File.separator + runId), projectName);
        ReportBuilder reportBuilder = new ReportBuilder(getJsonFiles(runId), config);
        reportBuilder.generateReports();
        return runId + com.publicissapient.anoroc.generator.HtmlReportGenerator.HTML_REPORT_FILE_PATH;
    }
    public void cleanReportDir(Long runId) {
        try {
            Path rootPath = Paths.get(reportPath+ File.separator + runId);
            if (Files.exists(rootPath)) {
                Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .peek(System.out::println)
                        .forEach(File::delete);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public FeatureReport getFeatureReport(FeatureResult result) {
        FeatureReport featureReport = null;
        try {
            Map<String, Object> resultMap = result.toMap();
            featureReport = FeatureReport.build(resultMap.get("name").toString());
            featureReport.setUri(resultMap.get("uri").toString());
            List<ScenarioOutlineReport> scenarioList = getScenarioOutlineReports(resultMap);
            featureReport.setScenarioOutlines(scenarioList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return featureReport;
    }


    private List<String> getJsonFiles(Long runId) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(reportPath+ File.separator + runId), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList(jsonFiles.size());
        jsonFiles.forEach(fileObj -> jsonPaths.add(fileObj.getAbsolutePath()));
        return jsonPaths;
    }

    private List<ScenarioOutlineReport> getScenarioOutlineReports(Map<String, Object> resultMap) {
        List<ScenarioOutlineReport> scenarioList = new ArrayList<>();
        List<Map> scenarioResultList = (List<Map>) resultMap.get("elements");
        scenarioResultList.forEach(scenario -> {
            buildScenarioList(scenarioList, scenario);
        });
        return scenarioList;
    }

    private void buildScenarioList(List<ScenarioOutlineReport> scenarioList, Map scenario) {
        ScenarioOutlineReport scenarioOutlineReport = buildScenarioOutlineReport(scenario);
        List<Map> stepResult = scenario.get("steps") != null ? (List<Map>) scenario.get("steps") : null;
        if (stepResult != null) {
            List<StepDefinitionReport> steps = new ArrayList<>();
            stepResult.forEach(step -> {
                StepDefinitionReport stepReport = getStepDefinitionReport(step);
                steps.add(stepReport);
            });
            scenarioOutlineReport.setSteps(steps);
            scenarioList.add(scenarioOutlineReport);
        }
    }

    private ScenarioOutlineReport buildScenarioOutlineReport(Map scenario) {
        String type = scenario.get("type") != null ? scenario.get("type").toString() : "";
        ScenarioOutlineReport scenarioOutlineReport = ScenarioOutlineReport.build(scenario.get("name") != null ? scenario.get("name").toString() : "");
        scenarioOutlineReport.setKeyword(scenario.get("keyword") != null ? scenario.get("keyword").toString() : "");
        scenarioOutlineReport.setType(type);
        return scenarioOutlineReport;
    }

    private StepDefinitionReport getStepDefinitionReport(Map step) {
        StepDefinitionReport stepReport = buildStepDefinitionReport(step);
        Map<String, Object> results = step.get("result") != null ? (Map) step.get("result") : null;
        if (results != null) {
            ResultReport resultReport = getResultReport(results);
            stepReport.setResult(resultReport);
        }
        if (step.get("embeddings") != null) {
            List<ScreenshotEmbedding> embeddings = getScreenshotEmbeddings(step);
            stepReport.setScreenshotEmbeddingList(embeddings);
        }
        return stepReport;
    }

    private List<ScreenshotEmbedding> getScreenshotEmbeddings(Map step) {
        List<ScreenshotEmbedding> embeddings = new ArrayList<>();
        ((List<Map>) step.get("embeddings")).forEach(embeds -> {
            ScreenshotEmbedding embed = ScreenshotEmbedding.build(embeds.get("data") != null ? embeds.get("data").toString() : "");
            embed.setMimeType(embeds.get("mime_type") != null ? embeds.get("mime_type").toString() : "");
            embeddings.add(embed);
        });
        return embeddings;
    }

    private StepDefinitionReport buildStepDefinitionReport(Map step) {
        StepDefinitionReport stepReport = StepDefinitionReport.build(step.get("name") != null ? step.get("name").toString() : "");
        stepReport.setKeyword(step.get("keyword").toString());
        stepReport.setMatch(stepReport.new Match(step.get("keyword") + " " + step.get("name")));
        return stepReport;
    }

    private ResultReport getResultReport(Map<String, Object> results) {
        ResultReport resultReport = ResultReport.build(results.get("name") != null ? results.get("name").toString() : "");
        resultReport.setDurationInNano(results.get("duration") != null ? (long) results.get("duration") : 0L);
        resultReport.setStatus(results.get("status") != null ? results.get("status").toString() : "");
        resultReport.setErrorMessage(results.get("error_message") != null ? results.get("error_message").toString() : null);
        return resultReport;
    }

}
