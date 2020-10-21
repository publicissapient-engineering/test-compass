package com.publicissapient.anaroc.builder;

import com.publicissapient.anaroc.executor.CommandExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class CommandExecutorFactory {

    private CommandExecutorFactory() {
    }

    public static CommandExecutor getCommandExecutor(WebDriver webDriver, Map<String, String> xPaths) {
        return CommandExecutor.build(webDriver, xPaths);
    }

}
