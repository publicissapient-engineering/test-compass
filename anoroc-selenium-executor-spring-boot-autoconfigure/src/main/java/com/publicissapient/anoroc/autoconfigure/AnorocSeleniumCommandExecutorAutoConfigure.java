package com.publicissapient.anoroc.autoconfigure;

import com.publicissapient.anaroc.executor.CommandExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(CommandExecutor.class)
public class AnorocSeleniumCommandExecutorAutoConfigure {

    @Bean
    public CommandExecutor commandExecutor() {
        return new CommandExecutor();
    }

}
