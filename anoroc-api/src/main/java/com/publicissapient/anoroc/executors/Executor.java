package com.publicissapient.anoroc.executors;

import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.report.RPReportGenerator;

import java.util.Optional;


public interface Executor {

    void run(RunPayload run, Optional<RPReportGenerator> rpReportGenerator);

}
