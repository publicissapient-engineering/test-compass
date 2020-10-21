package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.BusinessScenario;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Arrays;

import static com.publicissapient.anoroc.factory.AnorocFactoryTest.pageRequestBuilder;
import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.getBusinessScenarioEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class BusinessScenarioRepositoryTest {

    @Autowired
    private BusinessScenarioRepository businessScenarioRepository;

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/features/insert_features.sql")})
    @Test
    void should_be_able_to_save_business_scenario() {
        BusinessScenario businessScenario = getBusinessScenarioEntity(1l, Arrays.asList(1l));
        BusinessScenario result = businessScenarioRepository.save(businessScenario);
        BusinessScenario actualResult =businessScenarioRepository.findById(result.getId()).get();
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getFeatures()).isNotEmpty();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/features/insert_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/insert_business_scenario.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/insert_business_scenario_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/delete_business_scenario_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/delete_business_scenario.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/features/delete_features.sql")
    })
    @Test
    void should_be_able_to_get_business_scenario_for_an_id() {
        long businessScenarioId = 1l;
        assertThat(businessScenarioRepository.findById(businessScenarioId)).isPresent();
    }

    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/features/insert_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/insert_business_scenario.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/insert_business_scenario_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/delete_business_scenario_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/delete_business_scenario.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/features/delete_features.sql")
    })
    @Test
    void should_be_able_to_get_business_scenario_list() {
        assertThat(businessScenarioRepository.findAll(pageRequestBuilder(0, 5))).isNotEmpty();
    }


    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/application/insert_application.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/features/insert_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/insert_business_scenario.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/insert_business_scenario_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/delete_business_scenario_features.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/business_scenario/delete_business_scenario.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:sql/features/delete_features.sql")
    })
    @Test
    void should_return_business_scenario_count() {
        assertThat(businessScenarioRepository.count()).isGreaterThan(0);
    }
}
