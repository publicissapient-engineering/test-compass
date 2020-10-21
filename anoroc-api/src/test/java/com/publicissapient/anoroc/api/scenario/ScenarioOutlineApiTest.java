package com.publicissapient.anoroc.api.scenario;

import com.publicissapient.anoroc.payload.request.ScenarioOutlineRequest;
import com.publicissapient.anoroc.payload.response.ScenarioOutlineResponse;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ScenarioOutlineApiTest {

    @Autowired
    TestRestTemplate restTemplate;

    void should_save_scenario() {
        ResponseEntity<ScenarioOutlineResponse> responseEntity = restTemplate.postForEntity("/scenario/feature/1", new ScenarioOutlineRequest(), ScenarioOutlineResponse.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
