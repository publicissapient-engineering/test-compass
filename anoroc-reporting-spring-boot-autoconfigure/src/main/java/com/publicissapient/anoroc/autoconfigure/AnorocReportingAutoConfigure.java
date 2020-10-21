package com.publicissapient.anoroc.autoconfigure;

import com.publicissapient.anoroc.AnorocReporting;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AnorocReporting.class)
public class AnorocReportingAutoConfigure {

    @Bean
    public AnorocReporting anorocReporting(){
        return new AnorocReporting();
    }
}
