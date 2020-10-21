package com.publicissapient.anoroc.messaging.listeners;


import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.service.BusinessScenarioRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class BusinessScenarioRunInitListener {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private BusinessScenarioRunService businessScenarioRunService;

    @JmsListener(destination = "${anoroc.activemq.business.scenario.run.init.queue}", containerFactory = "jsaFactory")
    public void process(BusinessScenarioRunPayload businessScenarioRunPayload) throws IOException {
        log.debug("Received business scenario run payload {}"+ businessScenarioRunPayload.toString());
        businessScenarioRunService.run(businessScenarioRunPayload);
    }
}
