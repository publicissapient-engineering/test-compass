package com.publicissapient.anoroc.api.environment;


import com.publicissapient.anoroc.payload.response.EnvironmentCount;
import com.publicissapient.anoroc.payload.response.EnvironmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static com.publicissapient.anoroc.factory.AnorocFactoryTest.getUriVariables;
import static com.publicissapient.anoroc.factory.EnvironmentFactoryTest.getEnvRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class EnvironmentApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/env/delete_env.sql"})
    })
    @Test
    void should_be_able_get_an_env_by_id() {
        ResponseEntity<EnvironmentResponse> response = testRestTemplate.getForEntity("/environment/{id}", EnvironmentResponse.class, 1);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(1);
    }

    @Test
    void should_throw_env_not_found_exception_for_an_invalid_id() {
        ResponseEntity<EnvironmentResponse> response = testRestTemplate.getForEntity("/environment/{id}", EnvironmentResponse.class, 0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/env/delete_env.sql"})
    })
    @Test
    void should_be_able_to_get_an_env_list() {
        ResponseEntity<List> listResponses = testRestTemplate.getForEntity("/environment/list?page={page}&size={size}", List.class, getUriVariables());
        assertThat(listResponses.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listResponses.getBody()).isNotEmpty();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/env/delete_env.sql"})
    })
    @Test
    void should_be_able_get_count() {
        ResponseEntity<EnvironmentCount> response = testRestTemplate.getForEntity("/environment/count", EnvironmentCount.class, 0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCount()).isGreaterThan(0);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/env/delete_env.sql"})
    })
    @Test
    void should_be_able_save_env_obj() {
        ResponseEntity<EnvironmentResponse> response = testRestTemplate.postForEntity("/environment/save", getEnvRequest(), EnvironmentResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/env/delete_env.sql"})
    })
    @Test
    void should_be_able_update_env_obj() {
        ResponseEntity<EnvironmentResponse> response = testRestTemplate.postForEntity("/environment/save", getEnvRequest(1l), EnvironmentResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("dev_update");
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/env/delete_env.sql"})
    })
    @Test
    void should_be_able_to_get_all() {
        ResponseEntity<List> listResponses = testRestTemplate.getForEntity("/environment/all", List.class, getUriVariables());
        assertThat(listResponses.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listResponses.getBody()).isNotEmpty();
    }


}
