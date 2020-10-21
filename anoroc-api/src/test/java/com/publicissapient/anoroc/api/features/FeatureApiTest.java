package com.publicissapient.anoroc.api.features;

import com.publicissapient.anoroc.model.FeatureType;
import com.publicissapient.anoroc.payload.request.FeatureRequest;
import com.publicissapient.anoroc.payload.response.FeatureCount;
import com.publicissapient.anoroc.payload.response.FeatureResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class FeatureApiTest {

    public static final String FEATURE_COUNT_API = "/feature/count?contains=&notIn=0";
    public static final String FEATURE_LIST_API_WITH_PARAMS = "/feature/list?page={page}&size={size}";
    public static final String FEATURE_LIST_API = "/feature/list";
    public static final String FEATURE_SAVE = "/feature/save";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_return_feature_count_inserted_from_sql() {
        assertThat(featureCount()).isEqualTo(2);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_return_feature_list_with_one_feature_inserted_from_sql() {
        assertThat(featureListCount()).isEqualTo(2);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_return_feature_list_requested_with_pagination() {
        assertThat(featureListCountWithPagination()).isEqualTo(2);
    }


    @Test
    void should_not_return_feature_details() {
        ResponseEntity<FeatureResponse> featureResponseResponseEntity = getForFeatureDetails(3L);
        assertThat(featureResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_return_feature_details() {
        ResponseEntity<FeatureResponse> featureResponseResponseEntity = getForFeatureDetails(1l);
        assertThat(featureResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(featureResponseResponseEntity.getBody().getApplication()).isNotNull();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_be_able_to_get_include_feature_list() {
        ResponseEntity<List<FeatureResponse>> featureResponseResponseEntity = testRestTemplate.exchange(
                                                                                            "/feature/list?page=0&size=10&name=&notIn=1",
                                                                                                HttpMethod.GET,
                                                                                                HttpEntity.EMPTY,
                                                                                                new ParameterizedTypeReference<List<FeatureResponse>>() {
                                                                                                });
        assertThat(featureResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(featureResponseResponseEntity.getBody().size()).isEqualTo(1);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql",
                            "classpath:sql/features/insert_include_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_be_able_to_get_associated_feature_list() {
        ResponseEntity<List> featureResponseResponseEntity = testRestTemplate.getForEntity("/feature/associatedList/2", List.class);
        assertThat(featureResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(featureResponseResponseEntity.getBody().size()).isEqualTo(1);
    }



    void should_save_feature() {
        assertThat(featureSaveRequest("Bing search feature").getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void should_return_bad_request() {
        assertThat(featureSaveRequest("").getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_update_feature() {
        assertThat(updateFeatureDetails().getBody().getName()).isEqualTo("Simple Login Feature");
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_be_able_to_get_feature_count() {
        ResponseEntity<FeatureCount> responseEntity =  testRestTemplate.getForEntity(
                "/feature/count?contains=&notIn=0",
                FeatureCount.class);
        assertThat(responseEntity.getBody().getCount()).isEqualTo(2);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/insert_application.sql",
                            "classpath:sql/features/insert_features.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {"classpath:sql/features/delete_features.sql",
                            "classpath:sql/application/delete_application.sql"}),
    })
    @Test
    void should_be_able_to_get_feature_count_contains_param() {
        int countS = featureListCountWithPagination();
        ResponseEntity<FeatureCount> responseEntity =  testRestTemplate.getForEntity(
                "/feature/count?contains=bing&notIn=0",
                FeatureCount.class);
        assertThat(responseEntity.getBody().getCount()).isEqualTo(2);
    }

    private ResponseEntity<FeatureResponse> updateFeatureDetails() {
        FeatureRequest reqBody = FeatureRequest.builder().id(1L).name("Simple Login Feature").content("Scenario Content").xPath("").featureType(FeatureType.ANOROC).applicationId(1l).build();
        return testRestTemplate.postForEntity(
                FEATURE_SAVE,
                reqBody,
                FeatureResponse.class);
    }

    private ResponseEntity<FeatureResponse> featureSaveRequest(String featureName) {
        FeatureRequest reqBody = FeatureRequest.builder().name(featureName).content("Scenario Content").xPath("").featureType(FeatureType.ANOROC).applicationId(1l).build();
        return testRestTemplate.postForEntity(
                FEATURE_SAVE,
                reqBody,
                FeatureResponse.class);
    }

    private ResponseEntity<List<FeatureResponse>> featuresList() {
        return testRestTemplate.exchange(
                FEATURE_LIST_API,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<FeatureResponse>>() {
                });
    }

    private ResponseEntity<List<FeatureResponse>> featuresListWithPagination() {
        return testRestTemplate.exchange(
                FEATURE_LIST_API_WITH_PARAMS,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<FeatureResponse>>() {
                }, "0", "20");
    }

    private ResponseEntity<FeatureResponse> getForFeatureDetails(Long featureId) {
        return testRestTemplate.getForEntity(
                "/feature/" + featureId,
                FeatureResponse.class);
    }

    private ResponseEntity<FeatureCount> getForFeatureCount() {
        return testRestTemplate.getForEntity(
                FEATURE_COUNT_API,
                FeatureCount.class);
    }

    private long featureCount() {
        return getForFeatureCount().getBody().getCount();
    }

    private String featureDetailsName(Long featureId) {
        return getForFeatureDetails(featureId).getBody().getName();
    }

    private int featureListCount() {
        return featuresList().getBody().size();
    }

    private int featureListCountWithPagination() {
        return featuresListWithPagination().getBody().size();
    }
}
