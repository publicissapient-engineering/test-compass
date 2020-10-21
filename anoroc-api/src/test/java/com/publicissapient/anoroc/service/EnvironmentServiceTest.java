package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.EnvironmentNotFoundException;
import com.publicissapient.anoroc.factory.EnvironmentFactoryTest;
import com.publicissapient.anoroc.model.Environment;
import com.publicissapient.anoroc.repository.EnvironmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static com.publicissapient.anoroc.factory.EnvironmentFactoryTest.getEnvironment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EnvironmentServiceTest {

    @Autowired
    private EnvironmentService environmentService;

    @MockBean
    private EnvironmentRepository environmentRepository;

    @Test
    void should_be_able_to_save_env_obj() {
        when(environmentRepository.save(any())).thenReturn(getEnvironment(1l));
        Environment environment = environmentService.save(getEnvironment());
        assertThat(environment).isNotNull();
        assertThat(environment.getKarateContent()).isNotNull();
        assertThat(environment.getId()).isGreaterThan(0l);
    }

    @Test
    void should_be_able_to_get_env_by_id() {
        when(environmentRepository.findById(anyLong())).thenReturn(Optional.of(getEnvironment()));
        assertThat(environmentService.getEnv(1l)).isNotNull();
    }

    @Test
    void should_throw_env_not_found_exception_for_an_invalid_id() {
        when(environmentRepository.findById(anyLong())).thenThrow(new EnvironmentNotFoundException("not Found"));
        Assertions.assertThrows(EnvironmentNotFoundException.class, ()-> environmentService.getEnv(0l));
    }

    @Test
    void should_be_able_to_get_env_list() {
        when(environmentRepository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(environmentService.getEnvList(1, 5)).isEmpty();
    }

    @Test
    void should_be_able_to_get_all_env() {
        when(environmentRepository.findAll()).thenReturn(Arrays.asList(EnvironmentFactoryTest.getEnvironment(1l)));
        assertThat(environmentService.getAll()).isNotEmpty();
    }
}
