package com.publicissapient.anoroc.api.debug;

import com.publicissapient.anaroc.factory.DebugSessionFactory;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.payload.request.DebugRequest;
import com.publicissapient.anoroc.payload.response.DebugResponse;
import com.publicissapient.anoroc.payload.response.StartDebugResponse;
import com.publicissapient.anoroc.payload.response.StopDebugResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class DebugApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DebugSessionFactory sessionFactory;

    @AfterEach
    void tearDown() {
        sessionFactory.closeAllSessions();
    }

    @Test
    void should_be_able_to_start_debug_session() {
        ResponseEntity<StartDebugResponse> startDebugResponse = testRestTemplate.getForEntity("/debug/start", StartDebugResponse.class);
        assertThat(startDebugResponse.getStatusCode()).isEqualTo(OK);
        assertThat(startDebugResponse.getBody()).isNotNull();
        assertThat(startDebugResponse.getBody().getSessionId()).isNotEmpty();
    }

    @Test
    void should_return_not_found_for_an_invalid_debug_session() {
        ResponseEntity<StopDebugResponse> stopDebugResponse = testRestTemplate.getForEntity("/debug/stop?sessionId={sessionId}", StopDebugResponse.class, UUID.randomUUID().toString());
        assertThat(stopDebugResponse.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void should_be_able_to_stop_debug_session() {
        ResponseEntity<StartDebugResponse> startDebugResponse = testRestTemplate.getForEntity("/debug/start", StartDebugResponse.class);
        ResponseEntity<StopDebugResponse> stopDebugResponse = testRestTemplate.getForEntity("/debug/stop?sessionId={sessionId}", StopDebugResponse.class, startDebugResponse.getBody().getSessionId());
        assertThat(stopDebugResponse.getStatusCode()).isEqualTo(OK);
        assertThat(stopDebugResponse.getBody()).isNotNull();
        assertThat(stopDebugResponse.getBody().getSessionId()).isNotNull();
        assertThat(stopDebugResponse.getBody().getSessionId()).isEqualTo(startDebugResponse.getBody().getSessionId());
    }

    @Test
    void should_throw_not_found_for_run_when_session_is_invalid() {
        ResponseEntity<DebugResponse> debugResponse = testRestTemplate.postForEntity("/debug/run?sessionId={sessionId}",DebugRequest.builder().instruction("OPEN").build(), DebugResponse.class, UUID.randomUUID().toString());
        assertThat(debugResponse.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void should_be_able_to_return_ok_for_run() {
        ResponseEntity<StartDebugResponse> startDebugResponse = testRestTemplate.getForEntity("/debug/start", StartDebugResponse.class);
        ResponseEntity<DebugResponse> debugResponse = testRestTemplate.postForEntity("/debug/run?sessionId={sessionId}",
                DebugRequest.builder().instruction("OPEN [url]").xpath("https://www.google.com").build(),
                DebugResponse.class, startDebugResponse.getBody().getSessionId());
        assertThat(debugResponse.getStatusCode()).isEqualTo(OK);
        assertThat(debugResponse.getBody()).isNotNull();
        assertThat(debugResponse.getBody().getStatus()).isEqualTo(Status.PASSED.getRawName());
    }

    @Test
    void should_be_able_to_return_ok_with_failed_status_for_run() {
        ResponseEntity<StartDebugResponse> startDebugResponse = testRestTemplate.getForEntity("/debug/start", StartDebugResponse.class);
        ResponseEntity<DebugResponse> debugResponse = testRestTemplate.postForEntity("/debug/run?sessionId={sessionId}",
                DebugRequest.builder().instruction("OPEN [url]").xpath("").build(),
                DebugResponse.class, startDebugResponse.getBody().getSessionId());
        assertThat(debugResponse.getStatusCode()).isEqualTo(OK);
        assertThat(debugResponse.getBody()).isNotNull();
        assertThat(debugResponse.getBody().getStatus()).isEqualTo(Status.FAILED.getRawName());
    }
}
