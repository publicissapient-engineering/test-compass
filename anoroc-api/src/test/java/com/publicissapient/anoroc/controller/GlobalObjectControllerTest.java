package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.factory.GlobalObjectFactoryTest;
import com.publicissapient.anoroc.payload.mapper.GlobalObjectMapper;
import com.publicissapient.anoroc.payload.request.GlobalObjectRequest;
import com.publicissapient.anoroc.service.GlobalObjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GlobalObjectController.class)
public class GlobalObjectControllerTest extends AnorocBaseControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GlobalObjectService service;

    @MockBean
    private GlobalObjectMapper objectMapper;

    @Test
    void should_be_able_get_global_object_for_an_id() throws Exception {
        when(service.getById(anyLong())).thenReturn(GlobalObjectFactoryTest.getGlobalObject(1l));
        when(objectMapper.globalObjectResponsePayload(any())).thenReturn(GlobalObjectFactoryTest.getGlobalObjectResponse());
        mockMvc.perform(get("/globalObject/1")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_to_get_global_object_list() throws Exception {
        when(service.getList(anyInt(), anyInt())).thenReturn(Arrays.asList(GlobalObjectFactoryTest.getGlobalObject(1l)));
        when(objectMapper.globalObjectResponsePayloads(any())).thenReturn(Arrays.asList(GlobalObjectFactoryTest.getGlobalObjectResponse()));
        mockMvc.perform(get("/globalObject/list?page=0&size=5")).andExpect(status().isOk());
    }

    @Test
    void should_be_able_save_global_object() throws Exception {
        when(service.save(any(), anyLong())).thenReturn(GlobalObjectFactoryTest.getGlobalObject(1l));
        when(objectMapper.globalObjectResponsePayload(any())).thenReturn(GlobalObjectFactoryTest.getGlobalObjectResponse());
        mockMvc.perform(post("/globalObject/save").contentType(MediaType.APPLICATION_JSON).content(asJsonRequestBody(getGlobalObjectRequest()))).andExpect(status().isCreated());
    }

    @Test
    void shoulb_be_able_to_get_global_object_count() throws Exception {
        when(service.getCount()).thenReturn(1l);
        when(objectMapper.globalObjectCount(anyLong())).thenReturn(GlobalObjectFactoryTest.getGlobalObjectCount());
        mockMvc.perform(get("/globalObject/count")).andExpect(status().isOk());
    }

    @Test
    void shoulb_be_able_to_get_all_global_object() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(GlobalObjectFactoryTest.getGlobalObject(1l)));
        when(objectMapper.globalObjectResponsePayloads(any())).thenReturn(Arrays.asList(GlobalObjectFactoryTest.getGlobalObjectResponse()));
        mockMvc.perform(get("/globalObject/all")).andExpect(status().isOk());
    }

    private GlobalObjectRequest getGlobalObjectRequest() {
        return GlobalObjectRequest.builder().build();
    }
}
