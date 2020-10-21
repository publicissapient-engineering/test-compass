package com.publicissapient.anoroc.service;


import com.publicissapient.anoroc.factory.EnvironmentFactoryTest;
import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.factory.RunFactoryTest;
import com.publicissapient.anoroc.factory.RunPayLoadFactoryTest;
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

import static com.publicissapient.anoroc.factory.PayloadPublisherFactoryTest.getPayloadPublisher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FeaturesRunPayLoadPublisherTest {

    @Autowired
    private FeaturesRunPayLoadPublisher featuresRunQueuePublisherService;

    @MockBean
    private RunRepository runRepository;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private MessageSender sender;

    @MockBean
    private EnvironmentService environmentService;

    @MockBean
    private RunPayLoadGeneratorService payLoadGeneratorService;


    @Test
    void should_is_matches_with_run_type_returns_true() {
        assertThat(featuresRunQueuePublisherService.isRunTypeMatches(RunType.FEATURE)).isTrue();
    }

    @Test
    void should_save_the_run_and_publish_to_queue() throws Exception {
        when(featureService.featureById(anyLong())).thenReturn(FeatureFactory.feature(1l));
        when(runRepository.save(any())).thenReturn(RunFactoryTest.run(1l));
        when(environmentService.getEnv(anyLong())).thenReturn(EnvironmentFactoryTest.getEnvironment(1l));
        when(featureService.featureById(anyLong())).thenReturn(FeatureFactory.feature(1l));
        when(payLoadGeneratorService.generatePayLoad(any(), any())).thenReturn(RunPayLoadFactoryTest.getRunPayLoad());
        doNothing().when(sender).sendToRunInitQueue(any());
        Run run = featuresRunQueuePublisherService.saveAndPublishToQueue(getPayloadPublisher(1l, 1l, 1l, RunType.FEATURE));
        Assertions.assertThat(run.getId()).isEqualTo(1l);
        Assertions.assertThat(run.getStatus()).isEqualTo(RunStatus.RUNNING);
    }
}
