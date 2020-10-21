package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.ApplicationNotFoundException;
import com.publicissapient.anoroc.payload.mapper.ApplicationModelMapper;
import com.publicissapient.anoroc.payload.request.ApplicationRequest;
import com.publicissapient.anoroc.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ApplicationController.class)
@ActiveProfiles("test")
public class ApplicationControllerTest extends AnorocBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private ApplicationModelMapper modelMapper;

    @Test
    void should_be_able_save_application_req() throws Exception {
        mockMvc.perform(post("/applications/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonRequestBody(getApplicationRequest()))).andExpect(status().isCreated());
    }

    @Test
    void should_be_able_to_fetch_application_by_id() throws Exception {
        mockMvc.perform(get("/applications/1")).andExpect(status().isOk());
    }

    @Test
    void should_throw_application_not_found_exception_for_invalid_application_id() throws Exception {
        when(applicationService.getById(anyLong())).thenThrow(new ApplicationNotFoundException("Id not found"));
        mockMvc.perform(get("/applications/2")).andExpect(status().isNotFound());
    }

    @Test
    void should_be_able_to_fetch_all_applications_in_list_with_pagination() throws Exception {
        mockMvc.perform(get("/applications/list?page=0&size=0")).andExpect(status().isOk());
    }

    @Test
    void should_return_total_applications_count() throws Exception {
        when(applicationService.getCount()).thenReturn(1L);
        mockMvc.perform(get("/applications/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));
    }

    @Test
    void should_be_able_to_fetch_all_applications() throws Exception {
        mockMvc.perform(get("/applications/all")).andExpect(status().isOk());
    }

    public ApplicationRequest getApplicationRequest() {
        return ApplicationRequest.builder().name("Anoroc").build();
    }

}
