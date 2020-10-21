package com.publicissapient.anoroc.autoconfigure;

import com.publicissapient.anoroc.parser.FeatureParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(FeatureParser.class)
public class AnorocParserAutoConfigure {

    @Bean
    public FeatureParser featureParser() {
        return new FeatureParser();
    }

}
