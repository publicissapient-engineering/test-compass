package com.publicissapient.anoroc.messaging.listeners;


import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import com.publicissapient.anoroc.service.BusinessScenarioRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BusinessScenarioRunUpdateListener {

    @Autowired
    BusinessScenarioRunService businessScenarioRunService;

    @JmsListener(destination = "${anoroc.activemq.business.scenario.run.update.queue}", containerFactory = "jsaFactory")
    public void process(BusinessScenarioRunPayload runPayload) {
        log.debug("Received business scenario run payload for update "+runPayload.toString());
        businessScenarioRunService.updateRun(runPayload);
    }
}