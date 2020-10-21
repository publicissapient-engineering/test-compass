package com.publicissapient.anaroc.executor;

import com.publicissapient.anaroc.builder.CommandExecutorFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandExecutorFactoryTest {

    @Test
    void should_return_a_command_executor() {
        CommandExecutor commandExecutor = CommandExecutorFactory.getCommandExecutor(null, null);
        assertThat(commandExecutor).isNotNull();
    }
}
