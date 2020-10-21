package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.Environment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.publicissapient.anoroc.factory.EnvironmentFactoryTest.getEnvironment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace =  NONE)
public class EnvironmentRepositoryTest {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Test
    void should_be_able_to_save_obj() {
        Environment environment = environmentRepository.save(getEnvironment());
        assertThat(environment).isNotNull();
    }

    @SqlGroup(
            { @Sql(scripts = "/sql/env/insert_env.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                    @Sql(scripts = "/sql/env/delete_env.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            })
    @Test
    void should_be_able_to_get_obj() {
        assertThat(environmentRepository.getOne(1l)).isNotNull();
    }

    @SqlGroup(
            { @Sql(scripts = "/sql/env/insert_env.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                    @Sql(scripts = "/sql/env/delete_env.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            })
    @Test
    void should_be_able_to_get_list() {
        assertThat(environmentRepository.findAll()).isNotEmpty();
    }


    @SqlGroup(
            { @Sql(scripts = "/sql/env/insert_env.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                    @Sql(scripts = "/sql/env/delete_env.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            })
    @Test
    void should_be_able_to_get_count() {
        assertThat(environmentRepository.count()).isEqualTo(1l);
    }
}
