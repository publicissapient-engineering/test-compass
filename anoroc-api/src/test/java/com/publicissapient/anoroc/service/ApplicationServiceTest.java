package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.ApplicationNotFoundException;
import com.publicissapient.anoroc.factory.ApplicationFactoryTest;
import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.repository.ApplicationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @MockBean
    private ApplicationRepository applicationRepository;

    @Test
    void should_be_able_to_save_application_obj() {
        when(applicationRepository.save(any())).thenReturn(getApplication(1l));
        Application application = applicationService.save(null);
        Assertions.assertThat(application).isNotNull();
    }

    @Test
    void should_be_able_to_fetch_application_by_id() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(getApplication(1l)));
        Application application = applicationService.getById(1l);
        Assertions.assertThat(application).isNotNull();
    }

    @Test
    void should_throw_application_not_found_exception_for_an_invalid_id() {
        when(applicationRepository.findById(anyLong())).thenThrow(new ApplicationNotFoundException("Application not found for an id"));
        org.junit.jupiter.api.Assertions.assertThrows(ApplicationNotFoundException.class, ()->applicationService.getById(1l));
    }

    @Test
    void should_be_able_to_get_list_of_all_applications() {
        when(applicationRepository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        List<Application> applications = applicationService.getApplicationList(1, 1);
        Assertions.assertThat(applications).isEmpty();
    }

    @Test
    void should_return_total_application_count() {
        when(applicationRepository.count()).thenReturn(1L);
        Assertions.assertThat(applicationService.getCount()).isEqualTo(1L);
    }

    @Test
    void should_be_able_to_get_all_applications() {
        when(applicationRepository.findAll()).thenReturn(Arrays.asList(ApplicationFactoryTest.getApplication(1l)));
        List<Application> applications = applicationService.getAll();
        Assertions.assertThat(applications).isNotEmpty();
    }

    public Application getApplication(long id) {
        return Application.builder().id(id).name("Anoroc").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }


}
