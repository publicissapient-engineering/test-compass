package com.publicissapient.anoroc.report.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportGeneratorConfig {

    private String endpoint;
    private String apiKey;
    private String applicationName;
    private String launchName;

}
