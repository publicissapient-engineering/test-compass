package com.publicissapient.anoroc.controller;

import com.publicissapient.anaroc.exception.DebugSessionInvalidException;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.payload.request.DebugRequest;
import com.publicissapient.anoroc.service.DebugService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = DebugController.class)
public class DebugControllerTest extends  AnorocBaseControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DebugService debugService;

    @Test
    void should_return_ok_response() throws Exception {
        ResultActions result = mockMvc.perform(get("/debug/start"));
        assertThat(result.andExpect(status().isOk()));
    }

    @Test
    void should_return_not_found_for_stop_debug_if_session_invalid() throws Exception {
        when(debugService.stopDebug(anyString())).thenThrow(DebugSessionInvalidException.class);
        ResultActions result = mockMvc.perform(get("/debug/stop").queryParam("sessionId","invalidSessionId"));
        assertThat(result.andExpect(status().isNotFound()));
    }

    @Test
    void should_return_ok_for_stop_debug() throws Exception {
        ResultActions result = mockMvc.perform(get("/debug/stop").queryParam("sessionId", "validSessionId"));
        assertThat(result.andExpect(status().isOk()));
    }

    @Test
    void should_throw_bad_request_for_run_if_session_is_null() throws Exception {
        ResultActions result = mockMvc.perform(post("/debug/run")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonRequestBody(DebugRequest.builder().instruction("123").data("").build())));
        assertThat(result.andExpect(status().isBadRequest()));
    }

    @Test
    void should_throw_bad_request_for_run_if_instruction_is_null() throws Exception {
        ResultActions result = mockMvc.perform(post("/debug/run")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("sessionId","invalidSessionId")
                .content(asJsonRequestBody(DebugRequest.builder().data("").build())));
        assertThat(result.andExpect(status().isBadRequest()));
    }

    @Test
    void should_throw_not_found_for_run_if_instruction_is_invalid() throws Exception {
        when(debugService.execute(anyString(), any(), any())).thenThrow(DebugSessionInvalidException.class);
        ResultActions result = mockMvc.perform(post("/debug/run")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("sessionId","invalidSessionId")
                .content(asJsonRequestBody(DebugRequest.builder().instruction("OPEN").data("").build())));
        assertThat(result.andExpect(status().isNotFound()));
    }

    @Test
    void should_be_able_to_return_result_for_run() throws Exception {
        when(debugService.execute(anyString(), any(), any())).thenReturn(Result.builder().status(Status.PASSED).build());
        ResultActions result = mockMvc.perform(post("/debug/run")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("sessionId","validSessionId")
                .content(asJsonRequestBody(DebugRequest.builder().instruction("OPEN").data("").build())));
        assertThat(result.andExpect(status().isOk()));
    }

}


