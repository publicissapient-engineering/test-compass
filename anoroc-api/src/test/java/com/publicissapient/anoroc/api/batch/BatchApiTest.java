package com.publicissapient.anoroc.api.batch;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("Test")
public class BatchApiTest {
/*
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/application/insert_application.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/batch/delete_batch_business_scenario.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/batch/delete_batch.sql",
                            "classpath:sql/application/delete_application.sql"


                    })
    })
    void should_create_batch_with_list_of_business_scenario() {

        ResponseEntity<BatchResponse> responseEntity = restTemplate.postForEntity("/batch/save", BatchFactory.createRequest(), BatchResponse.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        System.out.println(responseEntity.getBody());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/batch/insert_batch.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/batch/insert_batch_business_scenario.sql"


                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/batch/delete_batch_business_scenario.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/batch/delete_batch.sql",
                            "classpath:sql/application/delete_application.sql"


                    })
    })
    void should_return_count_of_batches() {
        ResponseEntity<BatchCount> count = restTemplate.getForEntity("/batch/count", BatchCount.class);
        assertThat(count.getBody().getCount()).isEqualTo(2);

    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/batch/insert_batch.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/batch/insert_batch_business_scenario.sql"


                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/batch/delete_batch_business_scenario.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/batch/delete_batch.sql",
                            "classpath:sql/application/delete_application.sql"


                    })
    })
    void should_get_batch_list() {
        ResponseEntity<BatchResponse[]> response = restTemplate.getForEntity("/batch/list?page={page}&size={size}", BatchResponse[].class, "0", "5");
        assertThat(Arrays.asList(response.getBody()).size()).isGreaterThan(1);
        assertBatchResponse(Arrays.asList(response.getBody())
                .stream()
                .filter(batchResponse -> batchResponse.getId()== 1)
                .findAny()
                .get());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/batch/insert_batch.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/batch/insert_batch_business_scenario.sql"


                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/batch/delete_batch_business_scenario.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/batch/delete_batch.sql",
                            "classpath:sql/application/delete_application.sql"


                    })
    })
    void should_get_batch_list_by_application_id() {
        ResponseEntity<BatchResponse[]> response = restTemplate.getForEntity("/batch/list?page={page}&size={size}&applicationId={id}", BatchResponse[].class, "0", "5","1");
        assertThat(Arrays.asList(response.getBody()).size()).isGreaterThan(1);
        assertBatchResponse(Arrays.asList(response.getBody())
                .stream()
                .filter(batchResponse -> batchResponse.getId()== 1)
                .findAny()
                .get());

    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/application/insert_application.sql",
                            "classpath:sql/batch/insert_batch.sql",
                            "classpath:sql/business_scenario/insert_business_scenario.sql",
                            "classpath:sql/batch/insert_batch_business_scenario.sql"


                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/batch/delete_batch_business_scenario.sql",
                            "classpath:sql/business_scenario/delete_business_scenario.sql",
                            "classpath:sql/batch/delete_batch.sql",
                            "classpath:sql/application/delete_application.sql"


                    })
    })
    void should_get_batch_by_batch_id() {
        ResponseEntity<BatchResponse> batchResponse = restTemplate.getForEntity("/batch/{id}", BatchResponse.class, 1 );
        assertBatchResponse(batchResponse.getBody());
    }

    private void assertBatchResponse(BatchResponse batchResponse) {
        assertThat(batchResponse.getId()).isEqualTo(BatchFactory.response().getId());
        assertThat(batchResponse.getName()).isEqualTo(BatchFactory.response().getName());
        assertThat(batchResponse.getBusinessScenarios()).hasSize(1);
        assertThat(batchResponse.getApplication().getName()).isEqualTo(BatchFactory.response().getApplication().getName());
    }*/
}