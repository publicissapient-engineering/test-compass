package com.publicissapient.anoroc.autoconfigure;


import com.publicissapient.anoroc.report.factory.RPReportGeneratorFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RPReportGeneratorFactory.class)
public class AnorocReportPortalAutoConfigure {

    @Bean
    public RPReportGeneratorFactory rpReporter(){
        return new RPReportGeneratorFactory();
    }
}
