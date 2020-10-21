package com.publicissapient.anoroc.api.runs;

import com.publicissapient.anoroc.factory.RunFactoryTest;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.payload.response.FeatureResponse;
import com.publicissapient.anoroc.payload.response.RunCount;
import com.publicissapient.anoroc.payload.response.RunDetail;
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
import java.util.Map;

import static com.publicissapient.anoroc.factory.BusinessScenarioRunFactoryTest.getBusinessScenarioRunRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class RunApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void should_return_run_count() {
        assertThat(runCount()).isEqualTo(0);
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql",
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/runs/insert_runs.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql",
                            "classpath:sql/env/delete_env.sql"
                    }),
    })
    void should_return_total_run_count() {
        assertThat(runCount()).isEqualTo(12);
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql",
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/runs/insert_runs.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql",
                            "classpath:sql/env/delete_env.sql"
                    }),
    })
    void should_return_list_of_runs() {
        List<Map> runs = getRuns();
        assertThat(runs.size()).isEqualTo(10);
        verifyRunDetails(runs);
    }

    private void verifyRunDetails(List<Map> runs) {
        runs.forEach(runDetail -> {
            assertThat((int) runDetail.get("id")).isGreaterThan(0);
            assertThat(runDetail.get("details")).isNotNull();
            assertThat(runDetail.get("run_at")).isNotNull();
            assertThat(runDetail.get("status")).isNotNull();
            assertThat(runDetail.get("report_url")).isNotNull();
        });
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql",
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/runs/insert_runs.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql",
                            "classpath:sql/env/delete_env.sql"
                    }),
    })
    void should_return_second_page_list_of_runs() {
        List<Map> runs = getSecondPageRuns();
        assertThat(runs.size()).isEqualTo(2);
        verifyRunDetails(runs);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/env/insert_env.sql",
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/runs/delete_runs_features.sql",
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql",
                            "classpath:sql/env/delete_env.sql"
                    }),
    })
    @Test
    void should_run_feature_with_featureId() {
        HttpStatus statusCode = testRestTemplate.postForEntity("/run/feature", RunFactoryTest.featureRunRequest(), FeatureResponse.class).getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.ACCEPTED);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/env/insert_env.sql",
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario_run/delete_business_scenario_run.sql",
                            "classpath:sql/runs/delete_runs_features.sql",
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql",
                            "classpath:sql/env/delete_env.sql"
                    }),
    })
   @Test
    void should_be_able_to_run_business_scenario() {
        ResponseEntity<RunDetail> responseEntity = testRestTemplate.postForEntity("/run/businessScenario",
                getBusinessScenarioRunRequest(), RunDetail.class, new HashMap<>());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody().getId()).isGreaterThan(0);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(RunStatus.RUNNING.name());
    }

    private List getSecondPageRuns() {
        return testRestTemplate.getForEntity("/run/list?page={page}&size={size}", List.class, "1", "10").getBody();
    }

    private List getRuns() {
        return testRestTemplate.getForEntity("/run/list", List.class).getBody();
    }

    private long runCount() {
        return getCountResponse()
                .getCount();
    }

    private RunCount getCountResponse() {
        return testRestTemplate
                .getForEntity("/run/count", RunCount.class)
                .getBody();
    }
}
