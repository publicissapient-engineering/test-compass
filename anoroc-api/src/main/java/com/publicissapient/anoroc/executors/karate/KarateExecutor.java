package com.publicissapient.anoroc.executors.karate;

import com.intuit.karate.CallContext;
import com.intuit.karate.JsonUtils;
import com.intuit.karate.core.*;
import com.publicissapient.anoroc.exception.FeatureNotFoundException;
import com.publicissapient.anoroc.executors.Executor;
import com.publicissapient.anoroc.executors.reports.ReportGenerator;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.report.RPReportGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.intuit.karate.FileUtils.writeToFile;

@Component
@Slf4j
public class KarateExecutor implements Executor {

    @Value("${anoroc.runs.report.path}")
    private String reportPath;

    public static final String EXECUTOR_FEATURE_FILES_LOCATION = "executor";
    public static final String REPORT_STATIC_PATH = "static/";
    public static final String DEFAULT_XPATH_JSON_FILE = "karate.json";
    public static final String FEATURE_FILE_EXTENSION = ".feature";
    public static final String RESULT_JSON = "result.json";
    public static final String KARATE = "karate";
    public static final String OVERVIEW_FEATURES_HTML = "overview-features.html";

    public void run(RunPayload run, Optional<RPReportGenerator> rpReportGenerator) {
        try {
            log.info("Received run command for feature: " + run.getName());
            String featureUUID = UUID.randomUUID().toString();
            Path featureDirPath = getFeatureDir(run, featureUUID);
            Path filePath = getFile(run, featureDirPath);
            FeatureResult result = startFeatureExecution(run, rpReportGenerator, featureUUID, filePath);
            updateRun(run, result);
            clean(featureDirPath.toFile());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            run.setErrorDescription(e.getMessage());
            run.setStatus(RunStatus.ERROR);
        }

    }

    private FeatureResult startFeatureExecution(RunPayload run, Optional<RPReportGenerator> rpReportGenerator, String featureUUID, Path filePath) throws InterruptedException {
        Feature feature = parseFeature(filePath.toFile());
        if(rpReportGenerator.isPresent()) rpReportGenerator.get().startFeatureReport(feature.getName(), featureUUID);
        FeatureResult result = execute(feature, run);
        if(rpReportGenerator.isPresent()) rpReportGenerator.get().finishFeatureReport(new ReportGenerator().getFeatureReport(result), featureUUID);
        generateJsonReport(result, run);
        return result;
    }

    private void generateKarateHtmlReport(FeatureResult result, RunPayload run) {
        Engine.saveResultHtml(reportPath+ File.separator +
                        run.getRunId() + File.separator + KARATE,
                result,
                OVERVIEW_FEATURES_HTML);
        run.setReportPath(run.getRunId() + File.separator + KARATE + File.separator + OVERVIEW_FEATURES_HTML);
    }

    private void generateJsonReport(FeatureResult result, RunPayload run) {
        Map<String, Object> resultMap = result.toMap();
        updateFeatureNameAndSteps(run, resultMap);
        List<Map> single = Collections.singletonList(resultMap);
        String json = JsonUtils.toJson(single);
        File file = new File(reportPath+ File.separator +
                run.getRunId() + File.separator + run.getName().replace(" ", "_") + "_" + RESULT_JSON);
        writeToFile(file, json);
    }

    private FeatureResult execute(Feature feature, RunPayload runPayload) throws InterruptedException {
        return Engine.executeFeatureSync(null, feature, null, getCallContext(runPayload, feature));
    }

    private CallContext getCallContext(RunPayload runPayload, Feature feature) {
        // set the evalKarateConfig to false helps runner to use the env args stored in the environment repo.
        return new CallContext(runPayload.getArgs(), false, new ExecutionHook[0]);
    }

    private Feature parseFeature(File file) {
        return FeatureParser.parse(file);
    }

    private Path getFeatureDir(RunPayload run, String uuid) throws IOException {
        StringBuilder executorDir = new StringBuilder()
                .append(EXECUTOR_FEATURE_FILES_LOCATION).append(File.separator)
                .append(uuid).append(File.separator);
        Path executorDirPath = Paths.get(executorDir.toString());
        Files.createDirectories(executorDirPath);
        return executorDirPath;
    }

    private Path getFile(RunPayload run, Path executorDirPath) throws IOException {
        if (run.getContent().isEmpty()) {
            throw new FeatureNotFoundException("Feature content is empty!!");
        }
        Path parentFeaturePath = writeFile(executorDirPath, run.getContent(), run.getName());
        writeIncludeFeature(run, executorDirPath);
        return parentFeaturePath;
    }

    private Path writeFile(Path executorDirPath, String content, String featureName) throws IOException {
        Path path = Paths.get(executorDirPath.toString() + File.separator + featureName.replaceAll(" ", "_") + FEATURE_FILE_EXTENSION);
        return Files.write(path, content.getBytes());
    }

    private void writeIncludeFeature(RunPayload run, Path executorDirPath) throws IOException {
        if (CollectionUtils.isEmpty(run.getIncludeFeatureRunPayload())) {
            return;
        }
        for (RunPayload includePayload : run.getIncludeFeatureRunPayload()) {
            writeFile(executorDirPath, includePayload.getContent(), includePayload.getName());
            writeIncludeFeature(includePayload, executorDirPath);
        }
    }

    private void updateRun(RunPayload run, FeatureResult result) {
        if (result.isFailed()) {
            run.setStatus(RunStatus.FAILURE);
        } else {
            run.setStatus(RunStatus.SUCCESS);
        }
    }

    private void clean(File dir) throws IOException {
        if (!dir.isDirectory()) {
            dir.delete();
            return;
        }
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                clean(file);
            }
            System.out.println("Deleting " + file.getName());
            file.delete();
        }
        dir.delete();
    }

    private void updateFeatureNameAndSteps(RunPayload run, Map<String, Object> resultMap) {
        resultMap.put("name", run.getName());
        List<Map> scenarioList = (List<Map>) resultMap.get("elements");

        scenarioList.forEach(scenario -> {
            ((List<Map>) scenario.get("steps")).forEach(step -> {
                step.remove("match");
                HashMap<String, Object> match = new HashMap<>();
                match.put("location", step.get("keyword") + " " + step.get("name"));
                match.put("arguments", Collections.EMPTY_LIST);
                step.put("match", match);
            });
        });
    }
}
