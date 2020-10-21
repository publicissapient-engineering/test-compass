package com.publicissapient.anoroc.messaging.service;

import com.publicissapient.anoroc.executors.factory.ExecutorFactory;
import com.publicissapient.anoroc.executors.karate.KarateExecutor;
import com.publicissapient.anoroc.executors.reports.ReportGenerator;
import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.factory.RunPayLoadFactoryTest;
import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.service.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.getBusinessScenarioEntity;
import static com.publicissapient.anoroc.factory.BusinessScenarioRunPayloadFactoryTest.getBusinessScenarioRunPayload;
import static com.publicissapient.anoroc.factory.RunFactoryTest.run;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BusinessScenarioRunServiceTest {
    
    @Autowired
    private BusinessScenarioRunService businessScenarioRunService;

    @MockBean
    private BusinessScenarioService businessScenarioService;

    @MockBean
    private RunService runService;

    @MockBean
    private ExecutorFactory executorFactory;

    @MockBean
    private KarateExecutor karateExecutor;

    @MockBean
    private ReportGenerator reportGenerator;

    @MockBean
    private MessageSender sender;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private RunPayLoadGeneratorService payLoadGeneratorService;


    @Test
    void should_process_business_scenario_run_payload() throws Exception {
        when(businessScenarioService.getBusinessScenario(anyLong())).thenReturn(getBusinessScenarioEntity(1l, Arrays.asList(1l)));
        when(runService.getById(anyLong())).thenReturn(run(1l));
        when(executorFactory.getExecutor(any())).thenReturn(karateExecutor);
        when(reportGenerator.initiateHtmlReportGeneration(anyLong(), anyString())).thenReturn("report/url/path");
        when(featureService.featureById(anyLong())).thenReturn(FeatureFactory.feature(1l));
        when(payLoadGeneratorService.generatePayLoad(any(), any())).thenReturn(RunPayLoadFactoryTest.getRunPayLoad());
        doNothing().when(karateExecutor).run(any(),any());
        doNothing().when(sender).sendToBusinessScenarioRunUpdateQueue(any());
        BusinessScenarioRunPayload businessScenarioRunPayload = businessScenarioRunService.run(getBusinessScenarioRunPayload());
        Assertions.assertThat(businessScenarioRunPayload).isNotNull();
        Assertions.assertThat(businessScenarioRunPayload.getStatus()).isEqualTo(RunStatus.SUCCESS);
    }

    @Test
    void should_update_businesss_scenario_run() {
        when(runService.getById(anyLong())).thenReturn(run(1l));
        doNothing().when(runService).updateRun(any(BusinessScenarioRunPayload.class), any(Run.class));
        businessScenarioRunService.updateRun(getBusinessScenarioRunPayload());
    }
}
