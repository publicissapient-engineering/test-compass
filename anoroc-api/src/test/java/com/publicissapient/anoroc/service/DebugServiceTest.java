package com.publicissapient.anoroc.service;

import com.publicissapient.anaroc.exception.DebugSessionInvalidException;
import com.publicissapient.anaroc.factory.DebugSessionFactory;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class DebugServiceTest {

    @Autowired
    private DebugService service;

    @Autowired
    private DebugSessionFactory sessionFactory;

    @AfterEach
    void tearDown() {
        sessionFactory.closeAllSessions();
    }

    @Test
    void should_be_able_to_start_debug() throws Exception {
        boolean result = service.startDebug(UUID.randomUUID().toString());
        assertThat(result).isTrue();
    }

    @Test
    void should_throw_invalid_exception_for_invalid_sessionId() {
        Assertions.assertThrows(DebugSessionInvalidException.class, ()-> service.stopDebug(UUID.randomUUID().toString()));
    }

    @Test
    void should_be_able_to_stop_debug() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        service.startDebug(sessionId);
        boolean result = service.stopDebug(sessionId);
        assertThat(result).isTrue();
    }

    @Test
    void should_throw_debug_session_invalid_exception_for_execute() {
        Assertions.assertThrows(DebugSessionInvalidException.class, ()->service.execute(UUID.randomUUID().toString(), getStepDefinition(), new HashMap<>()));
    }

    @Test
    void should_return_result_for_execution_of_step_def() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        service.startDebug(sessionId);
        Result result = service.execute(sessionId, getStepDefinition(), getXpaths("url", "https://www.google.com"));
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isNotNull();
    }

    public StepDefinition getStepDefinition() {
       return   StepDefinition.build()
                .withInstruction("I OPEN [url]")
                .withData("url", "");
    }

    public Map<String, String> getXpaths(String key, String value) {
        Map<String, String> xpathMap = new HashMap<>();
        xpathMap.put(key, value);
        return  xpathMap;
    }
}
