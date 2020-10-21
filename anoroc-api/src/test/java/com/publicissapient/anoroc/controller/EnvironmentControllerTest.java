package com.publicissapient.anoroc.controller;


import com.publicissapient.anoroc.payload.mapper.EnvironmentModelMapper;
import com.publicissapient.anoroc.service.EnvironmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.publicissapient.anoroc.factory.EnvironmentFactoryTest.getEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EnvironmentController.class)
public class EnvironmentControllerTest extends  AnorocBaseControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EnvironmentService environmentService;

    @MockBean
    EnvironmentModelMapper mapper;

    @Test
    void should_be_able_get_env_by_id() throws Exception {
        mockMvc.perform(get("/environment/{id}","1")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_to_save_env() throws Exception {
        mockMvc.perform(post("/environment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonRequestBody(getEnvironment()))
        ).andExpect(status().isCreated());
    }

    @Test
    void should_be_able_get_list() throws Exception {
        mockMvc.perform(get("/environment/list?page=0&size=5")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_get_all() throws Exception {
        mockMvc.perform(get("/environment/all")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_get_count() throws Exception {
        mockMvc.perform(get("/environment/count")).andExpect(status().isOk());
    }
}
