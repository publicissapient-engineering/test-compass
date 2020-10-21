package com.publicissapient.anoroc.repository;


import com.publicissapient.anoroc.model.GlobalObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static com.publicissapient.anoroc.factory.AnorocFactoryTest.pageRequestBuilder;
import static com.publicissapient.anoroc.factory.GlobalObjectFactoryTest.getGlobalObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class GlobalObjectRepositoryTest {

    @Autowired
    private GlobalObjectRepository globalObjectRepository;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_save_global_object_() {
        GlobalObject globalObject = globalObjectRepository.save(getGlobalObject());
        assertThat(globalObject).isNotNull();
        assertThat(globalObject.getId()).isNotNull();
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_global_object_for_an_id() {
        GlobalObject globalObject = globalObjectRepository.getOne(1l);
        assertThat(globalObject).isNotNull();
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_all_global_object_list() {
        List<GlobalObject> globalObjectList = globalObjectRepository.findAll();
        assertThat(globalObjectList).isNotEmpty();
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_global_object_list() {
        List<GlobalObject> globalObjectList = globalObjectRepository.findAll(pageRequestBuilder(0, 5)).getContent();
        assertThat(globalObjectList).isNotEmpty();
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(scripts = "/sql/global_object/insert_global_object.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/global_object/delete_global_object.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_be_able_to_get_global_object_count() {
        long count = globalObjectRepository.count();
        assertThat(count).isGreaterThan(0);
    }
}
