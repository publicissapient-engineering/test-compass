package com.publicissapient.anoroc.report.reporter;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import com.epam.reportportal.service.ReportPortal;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import io.reactivex.Maybe;
import lombok.extern.slf4j.Slf4j;
import rp.com.google.common.base.Suppliers;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static rp.com.google.common.base.Strings.isNullOrEmpty;

@Slf4j
public class RPReporter {

    public Supplier<Launch> launch;
    public ConcurrentHashMap<String, Maybe<String>> featureIdMap;
    public Maybe<String> scenarioId;


    RPReporter(Supplier<Launch> launch) {
        this.launch = launch;
        featureIdMap = new ConcurrentHashMap<>();
    }

     RPReporter() {
        this(Suppliers.memoize(() -> {
            final ReportPortal reportPortal = ReportPortal.builder()
                    .build();
            StartLaunchRQ rq = startLaunchRQ(reportPortal);
            return reportPortal.newLaunch(rq);
        }));
    }

     protected RPReporter(ListenerParameters listenerParameters) {
        this(Suppliers.memoize(() -> {
            final ReportPortal reportPortal = ReportPortal.builder()
                    .withParameters(listenerParameters)
                    .build();
            StartLaunchRQ rq = startLaunchRQ(reportPortal);
            return reportPortal.newLaunch(rq);
        }));
    }

    public void startReport() {
        launch.get().start();
    }

    public String completeReport() {
        FinishExecutionRQ rq = new FinishExecutionRQ();
        rq.setEndTime(Calendar.getInstance().getTime());
        String launchUrl = getLaunchUrl();
        launch.get().finish(rq);
        return launchUrl;
    }

    private static StartLaunchRQ startLaunchRQ(ReportPortal reportPortal) {
        ListenerParameters parameters = reportPortal.getParameters();
        StartLaunchRQ rq = new StartLaunchRQ();
        rq.setName(parameters.getLaunchName());
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setMode(parameters.getLaunchRunningMode());
        if (!isNullOrEmpty(parameters.getDescription())) {
            rq.setDescription(parameters.getDescription());
        }
        rq.setStartTime(Calendar.getInstance().getTime());
        return rq;
    }
    private String getLaunchUrl() {
        ListenerParameters parameters = launch.get().getParameters();
        log.info("LAUNCH URL: {}/ui/#{}/launches/all/{}", parameters.getBaseUrl(), parameters.getProjectName(),
                System.getProperty("rp.launch.id"));
        return parameters.getBaseUrl() + "/ui/#" + parameters.getProjectName() + "/launches/all/" + System.getProperty("rp.launch.id");
    }

}
