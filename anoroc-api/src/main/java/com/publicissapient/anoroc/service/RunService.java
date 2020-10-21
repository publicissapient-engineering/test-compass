package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.RunTypeNotFoundException;
import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.repository.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RunService extends AnorocService {

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private FeaturesRunPayLoadPublisher featuresRunQueuePublisherService;

    @Autowired
    private BusinessScenarioRunPayLoadPublisher businessScenarioRunQueuePublisherService;

    private List<PayLoadPublisher> payLoadPublishers;

    @PostConstruct
    public void initRunService() {
        payLoadPublishers = new ArrayList<>();
        payLoadPublishers.add(featuresRunQueuePublisherService);
        payLoadPublishers.add(businessScenarioRunQueuePublisherService);
    }


    public long getCount() {
        return runRepository.count();
    }

    public Run getById(long runId) {
        return runRepository.findById(runId).orElseThrow(() -> getRunNotFoundException(runId));
    }
    public List<Run> runs(Integer page, Integer size) {
        return runRepository.findAll(pageRequestBuilder(page, size)).getContent();
    }

    public Run run(PayloadPublisher payloadRequest) throws Exception, RunTypeNotFoundException {
        for(PayLoadPublisher payLoadPublisher : payLoadPublishers) {
            if(payLoadPublisher.isRunTypeMatches(payloadRequest.getRunType())){
                return payLoadPublisher.saveAndPublishToQueue(payloadRequest);
            }
        }
        throw new RunTypeNotFoundException("RunType does not matches");
    }

    @Transactional
    public void updateRun(RunPayload runPayload) {
        //update back run with status
        Run run = getById(runPayload.getRunId());
        updateRunDetail(runPayload.getStatus(), runPayload.getReportPath(), run, runPayload.getErrorDescription());
        updateRun(runPayload, run);
    }

    public void updateRun(RunPayload runPayload, Run run) {
        updateRunDetail(runPayload.getStatus(), runPayload.getReportPath(), run,runPayload.getErrorDescription());
        runRepository.save(run);
    }

    public void updateRun(BusinessScenarioRunPayload runPayload, Run run) {
        updateRunDetail(runPayload.getStatus(), runPayload.getReportUrl(), run,"");
        runRepository.save(run);
    }

    private void updateRunDetail(RunStatus status, String reportPath, Run run, String errorDesc) {
        run.setStatus(status);
        run.setReportUrl(reportPath);
        run.setErrorDescription(errorDesc);
        run.setUpdatedAt(LocalDateTime.now());
    }

}
