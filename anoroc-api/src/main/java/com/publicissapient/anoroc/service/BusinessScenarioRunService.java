package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.executors.factory.ExecutorFactory;
import com.publicissapient.anoroc.executors.reports.ReportGenerator;
import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.report.RPReportGenerator;
import com.publicissapient.anoroc.report.config.ReportGeneratorConfig;
import com.publicissapient.anoroc.report.factory.RPReportGeneratorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusinessScenarioRunService extends AnorocService {

    @Autowired
    private RunService runService;

    @Autowired
    private BusinessScenarioService businessScenarioService;

    @Autowired
    private ExecutorFactory executorFactory;

    @Autowired
    private MessageSender sender;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private RPReportGeneratorFactory rpReportGeneratorFactory;

    @Autowired
    private RunPayLoadGeneratorService payLoadGeneratorService;

    @Transactional
    public BusinessScenarioRunPayload run(BusinessScenarioRunPayload businessScenarioRunPayload) {
        BusinessScenario businessScenario = businessScenarioService.getBusinessScenario(businessScenarioRunPayload.getBusinessScenarioId());
        Run run = runService.getById(businessScenarioRunPayload.getRunId());
        cleanReportDirectory(businessScenarioRunPayload.getRunId());
        executeBusinessScenario(businessScenarioRunPayload, businessScenario, run);
        sender.sendToBusinessScenarioRunUpdateQueue(businessScenarioRunPayload);
        return businessScenarioRunPayload;
    }

    private List<RunPayload> executeBusinessScenario(BusinessScenarioRunPayload businessScenarioRunPayload, BusinessScenario businessScenario, Run run) {
        List<RunPayload> featureRunPayloads = null;
        try {
            Map<String, Object> rpParams = payLoadGeneratorService.getJsonMap(run.getEnvironment().getSettingsContent());
            Optional<RPReportGenerator> rpReportGenerator = rpReportGeneratorFactory.getReportGenerator(getRPConfig(rpParams,getApplicationName(businessScenario),
                    businessScenario.getName()));
            if(rpReportGenerator.isPresent()) rpReportGenerator.get().startReport();
            featureRunPayloads = businessScenario.getFeatures().stream()
                    .map(feature -> executeFeature(feature, businessScenarioRunPayload, businessScenario, run, rpReportGenerator))
                    .collect(Collectors.toList());
            if(rpReportGenerator.isPresent()) rpReportGenerator.get().completeReport();
            generateReport(businessScenarioRunPayload, businessScenario);
            updateBusinessScenarioRunPayload(businessScenarioRunPayload, featureRunPayloads);

        }catch(Exception e){
            log.error(e.getMessage(),e);
            businessScenarioRunPayload.setStatus(RunStatus.ERROR);
            updateRun(businessScenarioRunPayload);
        }

        return featureRunPayloads;
    }



    private RunPayload executeFeature(FeatureEntity entity, BusinessScenarioRunPayload businessScenarioRunPayload, BusinessScenario businessScenario, Run run, Optional<RPReportGenerator> rpReportGenerator) {
        RunPayload featureRunPayload = null;
        try {

            featureRunPayload = payLoadGeneratorService.generatePayLoad(entity, run);
            executorFactory.getExecutor(entity.getFeatureType()).run(featureRunPayload, rpReportGenerator);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            featureRunPayload = RunPayload.builder().status(RunStatus.FAILURE).build();
        }
        return featureRunPayload;
    }

    private void generateReport(BusinessScenarioRunPayload businessScenarioRunPayload, BusinessScenario businessScenario) {
        String reportUrl = reportGenerator.initiateHtmlReportGeneration(businessScenarioRunPayload.getRunId(), businessScenario.getName());
        businessScenarioRunPayload.setReportUrl(reportUrl);
    }

    private void updateBusinessScenarioRunPayload(BusinessScenarioRunPayload businessScenarioRunPayload, List<RunPayload> featureRunPayloads) {
        boolean isAnyFeatureHasFailedStatus = featureRunPayloads.stream().filter(featureRunPayload -> featureRunPayload.getStatus().equals(RunStatus.FAILURE)).findAny().isPresent();
        businessScenarioRunPayload.setAnyFeatureHasFailedStatus(isAnyFeatureHasFailedStatus);
        businessScenarioRunPayload.setStatus(isAnyFeatureHasFailedStatus ? RunStatus.FAILURE : RunStatus.SUCCESS);
    }

    public void updateRun(BusinessScenarioRunPayload businessScenarioRunPayload) {
        Run run = runService.getById(businessScenarioRunPayload.getRunId());
        runService.updateRun(businessScenarioRunPayload, run);
    }

    private void cleanReportDirectory(Long runId) {
        reportGenerator.cleanReportDir(runId);
    }

    private ReportGeneratorConfig getRPConfig(Map<String, Object> payload, String applicationName, String launchName) {
        Object rpEndpoint = payload.get("rp.endpoint");
        Object rpApikey = payload.get("rp.apikey");
        return ReportGeneratorConfig.builder()
                .endpoint(rpEndpoint != null ? rpEndpoint.toString() : "")
                .apiKey(rpApikey != null ? rpApikey.toString() : "")
                .applicationName(applicationName)
                .launchName(launchName).build();
    }


    private String getApplicationName(BusinessScenario businessScenario) {
        return businessScenario.getFeatures().stream()
                .findFirst().get()
                .getApplication()
                .getName();
    }
}
