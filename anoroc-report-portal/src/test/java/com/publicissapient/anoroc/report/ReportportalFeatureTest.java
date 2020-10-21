package com.publicissapient.anoroc.report;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import com.epam.reportportal.service.ReportPortal;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.publicissapient.anoroc.model.report.FeatureReport;
import com.publicissapient.anoroc.report.config.ReportGeneratorConfig;
import com.publicissapient.anoroc.report.factory.RPReportGeneratorFactory;
import com.publicissapient.anoroc.report.reporter.AnorocRPReporter;
import com.publicissapient.anoroc.report.reporter.RPReporter;
import io.reactivex.Maybe;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReportPortal.class, Launch.class,AnorocRPReporter.class})
public class ReportportalFeatureTest {

    private AnorocRPReporter reporter;
    private Launch launch;

    @Before
    public void setUp()  {
        launch = mockLaunch();
        reporter = new AnorocRPReporter();
       // reporter.launch = () -> launch;
    }

    @Test
    public void should_be_throw_exception_for_no_endpoin_config() throws Exception {
        ReportGeneratorConfig config = ReportGeneratorConfig.builder()
                .endpoint("")
                .apiKey("")
                .applicationName("")
                .launchName("")
                .build();
        Optional<RPReportGenerator> generateReport = new RPReportGeneratorFactory().getReportGenerator(config);
        Assertions.assertThat(generateReport.isPresent()).isEqualTo(false);
    }

    @Test
    public void should_be_able_start_report() {
        reporter.startReport();
        verify(launch, times(1)).start();
    }

    @Test
    public void should_be_able_to_complete_report() {
        reporter.completeReport();
        verify(launch, times(1)).finish(any(FinishExecutionRQ.class));
    }

    @Test
    public void should_be_able_to_start_feature_report() {
        FeatureReport featureResult = mock(FeatureReport.class);
        when(featureResult.getName()).thenReturn("AnorocFeatureName");
        when(launch.startTestItem(any(StartTestItemRQ.class))).thenReturn(mock(Maybe.class));
        reporter.startFeatureReport(featureResult.getName() ,"AnorocFeatureName");
        verify(launch, times(1)).startTestItem(any(StartTestItemRQ.class));
    }

    @Test
    @SneakyThrows
    public void should_be_able_to_finish_feature_report() {
        FeatureReport featureResult = mock(FeatureReport.class);
        when(featureResult.getName()).thenReturn("AnorocFeatureName");
        ConcurrentHashMap<String, Maybe<String>> featureIdMap = new ConcurrentHashMap<>();
        featureIdMap.put("AnorocFeatureName", mock(Maybe.class));
        FieldSetter.setField(reporter, RPReporter.class.getField("featureIdMap"),featureIdMap);
        reporter.finishFeatureReport(featureResult,"AnorocFeatureName");
        verify(launch, times(1)).finishTestItem(any(Maybe.class), any(FinishTestItemRQ.class));
    }

    @SneakyThrows
    private Launch mockLaunch() {
        PowerMockito.mockStatic(ReportPortal.class);
        ReportPortal.Builder builder = mock(ReportPortal.Builder.class);
        Launch launch = mock(Launch.class);
        ReportPortal reportPortal = mock(ReportPortal.class);
        when(ReportPortal.class, "builder").thenReturn(builder);
        when(builder.build()).thenReturn(reportPortal);
        when(reportPortal.getParameters()).thenReturn(getListenerParameters());
        when(launch.getParameters()).thenReturn(getListenerParameters());
        when(reportPortal.newLaunch(any(StartLaunchRQ.class))).thenReturn(launch);

        return launch;
    }

    private ListenerParameters getListenerParameters() {
        ListenerParameters parameters = new ListenerParameters();
        parameters.setLaunchName("launch");
        parameters.setBaseUrl("url");
        parameters.setProjectName("project");
        System.setProperty("rp.launch.id", "launchId");
        return parameters;
    }


}
