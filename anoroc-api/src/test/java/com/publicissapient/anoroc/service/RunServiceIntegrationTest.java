package com.publicissapient.anoroc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@ActiveProfiles("test")
public class RunServiceIntegrationTest {


    @Autowired
    private RunService runService;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = {
                            "classpath:sql/env/insert_env.sql",
                            "classpath:sql/runs/insert_runs.sql"
                    }),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = {
                            "classpath:sql/runs/delete_runs.sql",
                            "classpath:sql/env/delete_env.sql"
                    })
    })
    void should_return_list_of_runs() {
        assertThat(runService.runs(0, 10).size()).isEqualTo(10);
        assertThat(runService.runs(1, 10).size()).isEqualTo(2);
    }

}
