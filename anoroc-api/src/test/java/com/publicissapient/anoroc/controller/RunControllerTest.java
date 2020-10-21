package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.factory.BusinessScenarioRunFactoryTest;
import com.publicissapient.anoroc.factory.RunFactoryTest;
import com.publicissapient.anoroc.payload.mapper.RunModelMapper;
import com.publicissapient.anoroc.service.RunService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RunController.class)
@ActiveProfiles("test")
public class RunControllerTest extends AnorocBaseControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RunModelMapper run;

    @MockBean
    private RunService runService;

    @Test
    void should_return_total_run_count() throws Exception {
        mockMvc.perform(get("/run/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andDo(print());
    }

    @Test
    void should_return_list_of_runs() throws Exception {
        mockMvc.perform(get("/run/list"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_able_run_feature() throws Exception {
        mockMvc.perform(post("/run/feature").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonRequestBody(RunFactoryTest.featureRunRequest())))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    void should_able_run_business_scenario() throws Exception {
        mockMvc.perform(post("/run/businessScenario").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonRequestBody(BusinessScenarioRunFactoryTest.getBusinessScenarioRunRequest())))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

}
