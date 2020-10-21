package com.publicissapient.anoroc.report.factory;


import com.publicissapient.anoroc.report.RPReportGenerator;
import com.publicissapient.anoroc.report.config.ReportGeneratorConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class RPReportGeneratorFactory {

    public Optional<RPReportGenerator> getReportGenerator(ReportGeneratorConfig config)  {
        try {
            return Optional.of(new RPReportGenerator(config));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return Optional.empty();
        }
    }
}
