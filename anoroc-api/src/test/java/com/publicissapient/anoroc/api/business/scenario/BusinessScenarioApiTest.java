package com.publicissapient.anoroc.api.business.scenario;

import com.publicissapient.anoroc.payload.response.BusinessScenarioCount;
import com.publicissapient.anoroc.payload.response.BusinessScenarioResponse;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.publicissapient.anoroc.factory.AnorocFactoryTest.getUriVariables;
import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.getBusinessScenarioPayLoad;
import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.getBusinessScenarioPayLoadWithoutFeatureIds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class BusinessScenarioApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Order(1)
    @Test
    void should_be_able_to_save_business_scenario_obj_return_status_code_created() {
       ResponseEntity<BusinessScenarioResponse> responseResponseEntity =  testRestTemplate.postForEntity("/businessScenario/save",
                                                                            getBusinessScenarioPayLoad(), BusinessScenarioResponse.class, new HashMap<>());
       assertThat(responseResponseEntity.getBody()).isNotNull();
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_be_able_to_update_business_scenario_obj_return_status_code_accepted() {
        ResponseEntity<BusinessScenarioResponse> responseResponseEntity =  testRestTemplate.postForEntity("/businessScenario/save",
                getBusinessScenarioPayLoad(1l), BusinessScenarioResponse.class, new HashMap<>());
        assertThat(responseResponseEntity.getBody()).isNotNull();
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_be_able_to_save_business_scenario_obj_without_featureIds() {
        ResponseEntity<BusinessScenarioResponse> responseResponseEntity =  testRestTemplate.postForEntity("/businessScenario/save",
                getBusinessScenarioPayLoadWithoutFeatureIds(), BusinessScenarioResponse.class, new HashMap<>());
        assertThat(responseResponseEntity.getBody()).isNotNull();
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_be_able_to_get_business_scenario_by_id() {
        ResponseEntity<BusinessScenarioResponse> savedResponseResponseEntity =  testRestTemplate.postForEntity("/businessScenario/save",
                getBusinessScenarioPayLoad(1l), BusinessScenarioResponse.class, new HashMap<>());
        long businessScenarioId = savedResponseResponseEntity.getBody().getId();
        ResponseEntity<BusinessScenarioResponse> responseResponseEntity =  testRestTemplate.getForEntity("/businessScenario/"+businessScenarioId,
                                                                                             BusinessScenarioResponse.class, new HashMap<>());
        assertThat(responseResponseEntity.getBody()).isNotNull();
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_throw_business_scenario_not_found_exception_for_an_invalid_id() {
        ResponseEntity<BusinessScenarioResponse> responseResponseEntity =  testRestTemplate.getForEntity("/businessScenario/0", BusinessScenarioResponse.class, new HashMap<>());
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_be_able_to_get_business_scenario_list() {
        ResponseEntity<List> responseResponseEntity =  testRestTemplate.getForEntity("/businessScenario/list?page={page}&size={size}", List.class, getUriVariables());
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseResponseEntity.getBody()).isNotEmpty();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_be_able_to_get_count() {
        ResponseEntity<BusinessScenarioCount> responseResponseEntity =  testRestTemplate.getForEntity("/businessScenario/count", BusinessScenarioCount.class, new HashMap<>());
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseResponseEntity.getBody().getCount()).isGreaterThan(0);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/business_scenario/insert_business_scenario_features.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/delete_business_scenario_features.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"
                    }),
    })
    @Test
    void should_be_able_to_remove_feature_from_business_scenario() {
        ResponseEntity<BusinessScenarioResponse> savedResponseResponseEntity =  testRestTemplate.postForEntity("/businessScenario/save",
                getBusinessScenarioPayLoad(Arrays.asList(1l, 2l)), BusinessScenarioResponse.class, new HashMap<>());
        ResponseEntity<BusinessScenarioResponse> responseResponseEntity =  testRestTemplate.postForEntity("/businessScenario/feature/remove",
                getBusinessScenarioPayLoad(savedResponseResponseEntity.getBody().getId(), 1l), BusinessScenarioResponse.class, new HashMap<>());
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseResponseEntity.getBody().getFeatures()).isNotEmpty();
        assertThat(responseResponseEntity.getBody().getFeatures().get(0).getId()).isEqualTo(2l);
    }
}
