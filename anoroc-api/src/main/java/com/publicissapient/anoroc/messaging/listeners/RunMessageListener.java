package com.publicissapient.anoroc.messaging.listeners;

import com.publicissapient.anoroc.executors.factory.ExecutorFactory;
import com.publicissapient.anoroc.executors.reports.ReportGenerator;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.report.RPReportGenerator;
import com.publicissapient.anoroc.report.config.ReportGeneratorConfig;
import com.publicissapient.anoroc.report.factory.RPReportGeneratorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class RunMessageListener {

    @Autowired
    private ExecutorFactory executorFactory;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private RPReportGeneratorFactory rpReportGeneratorFactory;


    @JmsListener(destination = "${anoroc.activemq.run.init.queue}", containerFactory = "jsaFactory")
    public void process(RunPayload payload) throws IOException {
        try {
            log.debug("Received run payload " + payload.getName() + " for engine " + payload.getFeatureType());
            reportGenerator.cleanReportDir(payload.getRunId());
            Optional<RPReportGenerator> rpReportGenerator = rpReportGeneratorFactory.getReportGenerator(reportGeneratorConfig(payload));
            if(rpReportGenerator.isPresent())
                rpReportGenerator.get().startReport();
            executorFactory.getExecutor(payload.getFeatureType()).run(payload,rpReportGenerator );
            if(rpReportGenerator.isPresent())
                rpReportGenerator.get().completeReport();
            if (payload.getStatus() != RunStatus.ERROR) {
                payload.setReportPath(reportGenerator.initiateHtmlReportGeneration(payload.getRunId(), payload.getName()));
            }
        }catch (Exception e){
            payload.setStatus(RunStatus.ERROR);
            payload.setErrorDescription(e.getMessage());
        }
        messageSender.sendToRunUpdateQueue(payload);
    }

    private ReportGeneratorConfig reportGeneratorConfig(RunPayload payload) {
        Object rpEndpoint = payload.getArgs().get("rp.endpoint");
        Object rpApikey = payload.getArgs().get("rp.apikey");
        return ReportGeneratorConfig.builder().endpoint(rpEndpoint != null ? rpEndpoint.toString() : "")
                .apiKey(rpApikey != null ? rpApikey.toString() : "")
                .applicationName(payload.getApplicationName())
                .launchName(payload.getName()).build();

    }


}
