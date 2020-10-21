package com.publicissapient.anoroc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicissapient.anoroc.factory.BatchFactory;
import com.publicissapient.anoroc.payload.mapper.BatchModelMapper;
import com.publicissapient.anoroc.payload.request.BatchRequest;
import com.publicissapient.anoroc.service.BatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BatchController.class)
@ActiveProfiles("Test")
public class BatchControllerTest extends AnorocBaseControllerTest{

    @MockBean
    BatchService batchService;

    @MockBean
    BatchModelMapper mapper;


    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_status_created() throws Exception {
        mockMvc.perform(post("/batch/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonRequestBody(BatchFactory.createRequest())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void should_return_count() throws Exception {
        when(batchService.count()).thenReturn(1l);
        mockMvc.perform(get("/batch/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));

    }

    @Test
    void should_return_list_of_batch() throws Exception {
        when(batchService.list(any())).thenReturn(Collections.singletonList(BatchFactory.createEntity()));
        mockMvc.perform(get("/batch/list?page=0&size=5"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_return_batch_details_by_id() throws Exception {
        when(batchService.get(1L)).thenReturn(BatchFactory.createEntity());
        when(mapper.response(batchService.get(1L))).thenReturn(BatchFactory.response());
        mockMvc.perform(get("/batch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.application.name").value("Application 1"))
                .andDo(print());
    }

    private String jsonRequestBody(BatchRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }

}
