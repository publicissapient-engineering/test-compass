package com.publicissapient.anoroc.report.reporter;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.ReportPortal;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;
import com.publicissapient.anoroc.model.report.*;
import com.publicissapient.anoroc.report.config.ReportGeneratorConfig;
import com.publicissapient.anoroc.report.exception.ApiKeyNotDefinedException;
import com.publicissapient.anoroc.report.exception.EndpointNotDefinedException;
import io.reactivex.Maybe;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.publicissapient.anoroc.report.constants.Constants.*;

@Slf4j
public class AnorocRPReporter extends RPReporter{

    private static  String defaultLaunchName = "SampleTest";

    private static String defaultApplicationName ="SUPERADMIN_PERSONAL";

    public AnorocRPReporter(){
        super();
    }
    public AnorocRPReporter(ReportGeneratorConfig generatorConfig) throws Exception {
        super(getListenerParameters(generatorConfig));
    }

    public void startFeatureReport(String featureName, String uuid) {
        StartTestItemRQ rq = new StartTestItemRQ();
        rq.setName(featureName);
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType("STORY");
        Maybe<String> featureId = this.launch.get().startTestItem(rq);
        this.featureIdMap.put(uuid, featureId);
    }

    public void finishFeatureReport(FeatureReport featureReport, String uuid) {
        if (!this.featureIdMap.containsKey(uuid)) {
            log.error("BUG: Trying to finish unspecified feature.");
        }
        for (ScenarioOutlineReport scenarioResult : featureReport.getScenarioOutlines()) {
            startScenario(scenarioResult, uuid);
            startSteps(scenarioResult);
            finishScenario(scenarioResult);
        }
        finishFeatureRQ(featureReport, uuid);
    }

    private void startSteps(ScenarioOutlineReport scenarioOutlineReport) {
        List<StepDefinitionReport> stepResultsToMap = scenarioOutlineReport.getSteps();
        for (StepDefinitionReport step : stepResultsToMap) {
            ResultReport stepResult = step.getResult();
            sendStepData(step, stepResult);
        }
    }

    private void finishFeatureRQ(FeatureReport featureReport, String uuid) {
        FinishTestItemRQ rq = new FinishTestItemRQ();
        rq.setEndTime(Calendar.getInstance().getTime());
        String status = featureReport.getScenarioOutlines().stream()
                .map(scenarioResult -> scenarioResult.getSteps().stream()
                        .filter(step -> step.getResult().getStatus().equalsIgnoreCase(FAILED)))
                .findAny().isPresent() ? FAILED : PASSED;
        rq.setStatus(status);

        this.launch.get().finishTestItem(this.featureIdMap.remove(uuid), rq);

    }

    private void sendStepData(StepDefinitionReport step, ResultReport stepResult) {
        String logLevel = PASSED.equalsIgnoreCase(stepResult.getStatus()) ? INFO_LEVEL : ERROR_LEVEL;
        String stepName = STEP_STRING + step.getName();
        if (step.getResult().getErrorMessage() != null) {
            sendLog(stepName + DOC_STRING_LOG + step.getResult().getErrorMessage(), logLevel);
        }
        if (step.getScreenshotEmbeddingList().size() > 0) {
            List<ScreenshotEmbedding> embedList = step.getScreenshotEmbeddingList();
            embedList.forEach(screenshotEmbedding -> sendLogWithAttachments(stepName, logLevel, screenshotEmbedding));
        } else {
            sendLog(stepName, logLevel);
        }
    }

    private void startScenario(ScenarioOutlineReport scenarioOutlineReport, String uuid) {
        StartTestItemRQ rq = new StartTestItemRQ();
        if(scenarioOutlineReport.getKeyword().equalsIgnoreCase("background")){
            rq.setName("BACKGROUND: " + scenarioOutlineReport.getName());
            rq.setType("BEFORE_CLASS");
        }else{
            rq.setName("SCENARIO: " + scenarioOutlineReport.getName());
            rq.setType("SCENARIO");
        }
        rq.setStartTime(Calendar.getInstance().getTime());
        this.scenarioId = this.launch.get().startTestItem(this.featureIdMap.get(uuid), rq);
    }

    private void finishScenario(ScenarioOutlineReport scenarioOutlineReport) {
        if (this.scenarioId == null) {
            log.error("BUG: Trying to finish unspecified scenario.");
            return;
        }
        FinishTestItemRQ rq = new FinishTestItemRQ();
        rq.setEndTime(Calendar.getInstance().getTime());
        rq.setStatus(scenarioOutlineReport.getSteps().stream()
                .filter(step -> step.getResult().getStatus().equalsIgnoreCase(FAILED))
                .findAny()
                .isPresent() ? FAILED : PASSED);
        this.launch.get().finishTestItem(this.scenarioId, rq);
        this.scenarioId = null;
    }

    private void sendLog(final String message, final String level) {
        ReportPortal.emitLog(itemId -> {
            SaveLogRQ rq = new SaveLogRQ();
            rq.setMessage(message);
            rq.setItemUuid(itemId);
            rq.setLevel(level);
            rq.setLogTime(Calendar.getInstance().getTime());
            return rq;
        });
    }

    private void sendLogWithAttachments(final String message, final String level, final ScreenshotEmbedding embed) {
        ReportPortal.emitLog(itemId -> {
            SaveLogRQ rq = new SaveLogRQ();
            rq.setMessage(message);
            rq.setItemUuid(itemId);
            rq.setLevel(level);
            rq.setLogTime(Calendar.getInstance().getTime());

            try {
                SaveLogRQ.File f = new SaveLogRQ.File();
                f.setContentType(embed.getMimeType());
                f.setContent(Base64.getDecoder().decode(embed.getData()));
                f.setName(UUID.randomUUID().toString());
                rq.setFile(f);
            } catch (Exception var4) {
                log.error("Cannot send file to ReportPortal", var4);
            }
            return rq;
        });
    }

    private static ListenerParameters getListenerParameters(ReportGeneratorConfig generatorConfig) throws Exception {
        configNullCheck(generatorConfig);
        ListenerParameters parameters = new ListenerParameters();
        parameters.setBaseUrl(generatorConfig.getEndpoint());
        parameters.setApiKey(generatorConfig.getApiKey());
        parameters.setLaunchName(generatorConfig.getLaunchName() != null? generatorConfig.getLaunchName().replace(" ","_") : defaultLaunchName);
        parameters.setProjectName(generatorConfig.getApplicationName() != null ? generatorConfig.getApplicationName().replace(" ","_") : defaultApplicationName);
        parameters.setEnable(true);
        parameters.setLaunchRunningMode(Mode.DEFAULT);
        return parameters;
    }

    private static void configNullCheck(ReportGeneratorConfig generatorConfig) throws EndpointNotDefinedException, ApiKeyNotDefinedException {
        if(generatorConfig.getEndpoint() == null || generatorConfig.getEndpoint().isEmpty() ){
            throw new EndpointNotDefinedException("ReportPortal endpoint not defined");
        }
        if(generatorConfig.getApiKey() == null || generatorConfig.getApiKey().isEmpty() ){
            throw new ApiKeyNotDefinedException("Api key not defined for reportportal endpoint");
        }
    }
}
