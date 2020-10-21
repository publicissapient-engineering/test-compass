package com.publicissapient.anoroc.messaging.sender;

import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${anoroc.activemq.run.update.queue}")
    private String updateQueue;

    @Value("${anoroc.activemq.run.init.queue}")
    private String initQueue;

    @Value("${anoroc.activemq.business.scenario.run.init.queue}")
    private String initBusinessScenarioRunQueue;

    @Value("${anoroc.activemq.business.scenario.run.update.queue}")
    private String updateBusinessScenarioRunQueue;

    public void sendToRunInitQueue(RunPayload run){
        jmsTemplate.convertAndSend(initQueue,run);
    }

    public void sendToRunUpdateQueue(RunPayload run){
        jmsTemplate.convertAndSend(updateQueue, run);
    }

    public void sendToBusinessScenarioRunInitQueue(BusinessScenarioRunPayload scenarioRunPayload) {
        jmsTemplate.convertAndSend(initBusinessScenarioRunQueue, scenarioRunPayload);
    }

    public void sendToBusinessScenarioRunUpdateQueue(BusinessScenarioRunPayload scenarioRunPayload) {
        jmsTemplate.convertAndSend(updateBusinessScenarioRunQueue, scenarioRunPayload);
    }

}
