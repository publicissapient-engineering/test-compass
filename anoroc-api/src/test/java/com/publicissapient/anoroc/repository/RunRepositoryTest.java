package com.publicissapient.anoroc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("test")
public class RunRepositoryTest {

    @Autowired
    private RunRepository runRepository;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/env/insert_env.sql",
                            "classpath:sql/runs/insert_runs.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/runs/delete_runs_features.sql",
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/env/delete_env.sql"
                    })
    })
    void should_return_run_count() {
        assertThat(runRepository.count()).isEqualTo(12);
    }
}
