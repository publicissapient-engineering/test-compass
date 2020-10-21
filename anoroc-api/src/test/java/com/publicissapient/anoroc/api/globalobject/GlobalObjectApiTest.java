package com.publicissapient.anoroc.api.globalobject;

import com.publicissapient.anoroc.factory.GlobalObjectFactoryTest;
import com.publicissapient.anoroc.payload.response.GlobalObjectCount;
import com.publicissapient.anoroc.payload.response.GlobalObjectResponse;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GlobalObjectApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_an_object_for_an_id() {
        ResponseEntity<GlobalObjectResponse> responseEntity = testRestTemplate.getForEntity("/globalObject/1", GlobalObjectResponse.class, new HashMap<>());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_save_global_object() {
        ResponseEntity<GlobalObjectResponse> responseEntity = testRestTemplate.postForEntity("/globalObject/save", GlobalObjectFactoryTest.getGlobalObjectRequest(),
                                                                                                    GlobalObjectResponse.class, new HashMap<>());
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_list_of_global_object() {
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/globalObject/list?page=0&size=5", List.class, new HashMap<>());
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_all_global_object() {
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/globalObject/all", List.class, new HashMap<>());
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_count_of_global_object() {
        ResponseEntity<GlobalObjectCount> responseEntity = testRestTemplate.getForEntity("/globalObject/count", GlobalObjectCount.class, new HashMap<>());
        assertThat(responseEntity.getBody().getCount()).isGreaterThan(0l);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_update_global_object() {
        ResponseEntity<GlobalObjectResponse> responseEntity = testRestTemplate.postForEntity("/globalObject/save", GlobalObjectFactoryTest.getGlobalObjectRequest(1l),
                GlobalObjectResponse.class, new HashMap<>());
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo("sample_object_repo");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }


}
