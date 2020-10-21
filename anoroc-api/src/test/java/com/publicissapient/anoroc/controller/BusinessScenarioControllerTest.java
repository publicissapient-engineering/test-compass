package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.BusinessScenarioNotFoundException;
import com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest;
import com.publicissapient.anoroc.payload.mapper.BusinessScenarioModelMapper;
import com.publicissapient.anoroc.service.BusinessScenarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BusinessScenarioController.class)
public class BusinessScenarioControllerTest extends AnorocBaseControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessScenarioService businessScenarioService;

    @MockBean
    private BusinessScenarioModelMapper modelMapper;

    @Test
    void should_be_able_to_save_business_scenario_feature() throws Exception {
        when(modelMapper.businessScenario(any())).thenReturn(getBusinessScenarioEntity(0l, Arrays.asList(1l)));
        when(businessScenarioService.save(any(), anyList())).thenReturn(getBusinessScenarioEntity(1l, Arrays.asList(1l)));
        mockMvc.perform(post("/businessScenario/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonRequestBody(getBusinessScenarioRequest()))).andExpect(status().isCreated());
    }

    @Test
    void should_be_able_to_get_business_scenario_byId() throws Exception {
        when(businessScenarioService.getBusinessScenario(anyLong())).thenReturn(getBusinessScenarioEntity(1l, Arrays.asList(1l)));
        mockMvc.perform(get("/businessScenario/1")).andExpect(status().isOk());
    }

    @Test
    void should_throw_business_scenario_not_found_exception_for_an_invalid_id() throws Exception {
        when(businessScenarioService.getBusinessScenario(anyLong())).thenThrow(new BusinessScenarioNotFoundException("Business Scenario not found"));
        mockMvc.perform(get("/businessScenario/1")).andExpect(status().isNotFound());
    }

    @Test
    void should_be_able_get_list_of_business_scenario_for_a_page() throws Exception {
        when(businessScenarioService.getBusinessScenarios(any())).thenReturn(Arrays.asList(getBusinessScenarioEntity(1l, Arrays.asList(1l))));
        mockMvc.perform(get("/businessScenario/list?page=0&size=5")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_to_get_list_of_business_scenario_size() throws Exception {
        when(businessScenarioService.getCount()).thenReturn(1l);
        mockMvc.perform(get("/businessScenario/count")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_to_remove_feature_from_a_business_scenario() throws Exception {
        when(businessScenarioService.removeFeatureFromBusinessScenario(anyLong(), anyList())).thenReturn(BusinessScenarioFactoryTest.getBusinessScenarioEntity(1l, Arrays.asList(1l)));
        mockMvc.perform(post("/businessScenario/feature/remove").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonRequestBody(getBusinessScenarioPayLoad(1l, 1l)))).andExpect(status().isOk());
    }
}
