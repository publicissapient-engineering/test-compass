package com.publicissapient.anoroc.repository;


import com.publicissapient.anoroc.model.Application;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.publicissapient.anoroc.factory.ApplicationFactoryTest.getApplication;
import static com.publicissapient.anoroc.factory.ApplicationFactoryTest.pageRequestBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    void should_able_to_save_application() {
        Application application = applicationRepository.save(getApplication(2l));
        assertThat(application).isNotNull();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_able_to_return_list_of_application() {
        assertThat(applicationRepository.findAll(pageRequestBuilder(0, 5))).isNotEmpty();
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    @Order(1)
    void should_return_application_by_id() {
        assertThat(applicationRepository.findById(1l)).isPresent();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {"classpath:sql/application/delete_application.sql",
                            "classpath:sql/application/insert_application.sql"}),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/application/delete_application.sql")
    })
    @Test
    void should_return_applications_count() {
        assertThat(applicationRepository.count()).isEqualTo(1L);
    }
}
