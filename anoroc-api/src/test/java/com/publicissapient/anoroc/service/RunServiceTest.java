package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.RunTypeNotFoundException;
import com.publicissapient.anoroc.factory.PayloadPublisherFactoryTest;
import com.publicissapient.anoroc.factory.RunFactoryTest;
import com.publicissapient.anoroc.factory.RunPayLoadFactoryTest;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunType;
import com.publicissapient.anoroc.repository.RunRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@ActiveProfiles("test")
public class RunServiceTest {


    @Autowired
    private RunService runService;

    @MockBean
    private RunRepository runRepository;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private MessageSender sender;

    @MockBean
    private FeaturesRunPayLoadPublisher featuresRunQueuePublisherService;

    @MockBean
    private BusinessScenarioRunPayLoadPublisher businessScenarioRunQueuePublisherService;

    @Test
    void should_return_run_count() {
        when(runRepository.count()).thenReturn(1L);
        assertThat(runService.getCount()).isEqualTo(1);
        verify(runRepository).count();
    }

    @Test
    void should_return_list_of_runs() {
        when(runRepository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(runService.runs(0, 10).size()).isEqualTo(0);
    }

    @Test
    void should_be_able_to_update_run_from_payload() {
        when(runRepository.findById(anyLong())).thenReturn(Optional.of(RunFactoryTest.run(1l)));
        when(runRepository.save(any())).thenReturn(RunFactoryTest.run(1l));
        runService.updateRun(RunPayLoadFactoryTest.getRunPayLoad());
    }

    @Test
    void should_be_able_to_update_run() {
        when(runRepository.save(any())).thenReturn(RunFactoryTest.run(1l));
        runService.updateRun(RunPayLoadFactoryTest.getRunPayLoad(), RunFactoryTest.run(1l));
    }

    @Test
    void should_be_able_to_run_the_feature_payload() throws Exception, RunTypeNotFoundException {
        when(featuresRunQueuePublisherService.isRunTypeMatches(any())).thenReturn(true);
        when(featuresRunQueuePublisherService.saveAndPublishToQueue(any())).thenReturn(RunFactoryTest.run(1l));
        Run run =runService.run(PayloadPublisherFactoryTest.getPayloadPublisher(1l, 1l, 1l, RunType.FEATURE));
        assertThat(run).isNotNull();
    }

    @Test
    void should_be_able_to_run_the_business_scenario_payload() throws Exception, RunTypeNotFoundException {
        when(businessScenarioRunQueuePublisherService.isRunTypeMatches(any())).thenReturn(true);
        when(businessScenarioRunQueuePublisherService.saveAndPublishToQueue(any())).thenReturn(RunFactoryTest.run(1l));
        Run run = runService.run(PayloadPublisherFactoryTest.getPayloadPublisher(1l, 1l, 1l, RunType.BUSINESS_SCENARIO));
        assertThat(run).isNotNull();

    }

    @Test
    void should_be_able_to_throw_run_type_match_not_found_exception() throws Exception, RunTypeNotFoundException {
        when(businessScenarioRunQueuePublisherService.isRunTypeMatches(any())).thenReturn(false);
        when(featuresRunQueuePublisherService.isRunTypeMatches(any())).thenReturn(false);
        Assertions.assertThrows(RunTypeNotFoundException.class, ()-> runService.run(PayloadPublisherFactoryTest.getPayloadPublisher(1l, 1l, 1l, RunType.BUSINESS_SCENARIO)));

    }
}
