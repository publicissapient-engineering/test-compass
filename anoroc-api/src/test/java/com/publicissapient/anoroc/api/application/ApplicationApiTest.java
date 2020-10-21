package com.publicissapient.anoroc.api.application;

import com.publicissapient.anoroc.payload.response.ApplicationCount;
import com.publicissapient.anoroc.payload.response.ApplicationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.List;

import static com.publicissapient.anoroc.factory.ApplicationFactoryTest.getRequestPayload;
import static com.publicissapient.anoroc.factory.ApplicationFactoryTest.getUriVariables;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void should_able_to_save_application() {
        ResponseEntity<ApplicationResponse> responseEntity = testRestTemplate.postForEntity("/applications/save", getRequestPayload(), ApplicationResponse.class, new HashMap<>());
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isGreaterThan(0L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    void should_get_application_by_id() {
        ResponseEntity<ApplicationResponse> responseForPostEntity = testRestTemplate.postForEntity("/applications/save", getRequestPayload(), ApplicationResponse.class, new HashMap<>());
        Long applicationId = responseForPostEntity.getBody().getId();
        ResponseEntity<ApplicationResponse> responseEntity = testRestTemplate.getForEntity("/applications/{id}", ApplicationResponse.class, applicationId);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(applicationId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/delete_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/features/insert_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/features/delete_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_get_application_with_feature_count() {
        ResponseEntity<ApplicationResponse> responseEntity = testRestTemplate.getForEntity("/applications/{id}", ApplicationResponse.class, 1l);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getFeatureCount()).isEqualTo(2);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void should_throw_not_found_exception_for_an_invalid_id() {
        ResponseEntity<ApplicationResponse> responseEntity = testRestTemplate.getForEntity("/applications/{id}", ApplicationResponse.class, 0l);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void should_get_application_list_with_pagination() {
        ResponseEntity<ApplicationResponse> responseForPostEntity = testRestTemplate.postForEntity("/applications/save", getRequestPayload(), ApplicationResponse.class, new HashMap<>());
        Long applicationId = responseForPostEntity.getBody().getId();
        ResponseEntity<List> responses = testRestTemplate.getForEntity("/applications/list?page={page}&size={size}", List.class, getUriVariables());
        assertThat(responses.getBody()).isNotEmpty();
    }


    @Test
    void should_return_total_application_count() {
        ResponseEntity<ApplicationResponse> responseForPostEntity = testRestTemplate.postForEntity("/applications/save", getRequestPayload(), ApplicationResponse.class, new HashMap<>());
        ResponseEntity<ApplicationCount> responseEntity = testRestTemplate.getForEntity("/applications/count", ApplicationCount.class);
        assertThat(responseEntity.getBody().getCount()).isEqualTo(1l);
    }


    @Test
    void should_be_able_to_get_all_applications_in_a_list() {
        ResponseEntity<ApplicationResponse> responseForPostEntity = testRestTemplate.postForEntity("/applications/save", getRequestPayload(), ApplicationResponse.class, new HashMap<>());
        Long applicationId = responseForPostEntity.getBody().getId();
        ResponseEntity<List> responses = testRestTemplate.getForEntity("/applications/all", List.class);
        assertThat(responses.getBody()).isNotEmpty();

    }
}
