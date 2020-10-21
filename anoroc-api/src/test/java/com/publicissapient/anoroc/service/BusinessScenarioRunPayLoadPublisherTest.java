package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.factory.EnvironmentFactoryTest;
import com.publicissapient.anoroc.factory.RunFactoryTest;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.model.RunType;
import com.publicissapient.anoroc.repository.RunRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.getBusinessScenarioEntity;
import static com.publicissapient.anoroc.factory.BusinessScenarioRunPayloadFactoryTest.getBusinessScenarioRunPayload;
import static com.publicissapient.anoroc.factory.PayloadPublisherFactoryTest.getPayloadPublisher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class BusinessScenarioRunPayLoadPublisherTest {

    @Autowired
    private BusinessScenarioRunPayLoadPublisher businessScenarioRunQueuePublisherService;

    @MockBean
    private RunRepository runRepository;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private BusinessScenarioService businessScenarioService;

    @MockBean
    private EnvironmentService environmentService;

    @MockBean
    private MessageSender sender;

    @Test
    void should_be_able_to_save_and_publish_to_queue() throws Exception {
        when(businessScenarioService.getBusinessScenario(anyLong())).thenReturn(getBusinessScenarioEntity(1l, Arrays.asList(1l)));
        when(runRepository.save(any())).thenReturn(RunFactoryTest.run(1l));
        doNothing().when(sender).sendToBusinessScenarioRunInitQueue(getBusinessScenarioRunPayload());
        when(environmentService.getEnv(anyLong())).thenReturn(EnvironmentFactoryTest.getEnvironment(1l));
        Run run = businessScenarioRunQueuePublisherService.saveAndPublishToQueue(getPayloadPublisher(1l, 1l, 1l, RunType.BUSINESS_SCENARIO));
        Assertions.assertThat(run.getId()).isNotNull();
        Assertions.assertThat(run.getStatus()).isEqualTo(RunStatus.RUNNING);
    }

    @Test
    void should_is_matches_with_run_type_returns_true() {
        assertThat(businessScenarioRunQueuePublisherService.isRunTypeMatches(RunType.BUSINESS_SCENARIO)).isTrue();
    }
}
