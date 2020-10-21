package com.publicissapient.anoroc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicissapient.anoroc.exception.FeatureNotFoundException;
import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.payload.mapper.FeatureModelMapper;
import com.publicissapient.anoroc.payload.request.FeatureRequest;
import com.publicissapient.anoroc.service.FeatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.publicissapient.anoroc.factory.FeatureFactory.featureRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeatureController.class)
@ActiveProfiles("test")
public class FeatureControllerTest extends AnorocBaseControllerTest{

    public static final String FEATURE_LIST = "/feature/list";
    public static final String FEATURE_COUNT = "/feature/count?contains=&notIn=0";
    public static final String SIZE = "size";
    public static final String PAGE = "page";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private FeatureModelMapper mapper;

    @Test
    void should_return_total_feature_count() throws Exception {
        mockMvc.perform(get(FEATURE_COUNT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andDo(print());
    }

    @Test
    void should_return_feature_list() throws Exception {
        mockFeatureList();
        mockMvc.perform(get(FEATURE_LIST).param(SIZE, "5").param(PAGE, "0"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_return_include_feature_list() throws Exception {
        when(featureService.includeFeatureList(0, 5, "", 1))
                .thenReturn(Collections.singletonList(FeatureFactory.feature()));
        mockMvc.perform(get(FEATURE_LIST).param(SIZE, "5").param(PAGE, "0"))
                .andExpect(status().isOk())
                .andDo(print());
        // verifyFeatureList();
    }

    @Test
    void should_not_return_feature_details() throws Exception {
        when(featureService.featureById(3L)).thenThrow(FeatureNotFoundException.class);
        mockMvc. perform(get("/feature/3"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void should_return_feature_list_contains_name() throws Exception {
        mockMvc. perform(get("/feature/list?page=0&size=5&name=bing"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_save_feature_object() throws Exception {
        long applicationId = 0;
        FeatureRequest requestObject = featureRequest();
        mockSaveServiceCalls(requestObject);
        mockMvc.perform(post("/feature/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AsJsonRequestBody(requestObject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void should_return_associated_feature_list() throws Exception {
        mockMvc. perform(get("/feature/associatedList/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void verifySaveServiceCalls(FeatureRequest requestObject) {
        verify(mapper.featureEntity(requestObject));
        verify(featureService.save(any(FeatureEntity.class), anyLong()));
        verify(mapper.featurePayload(any(FeatureEntity.class)));
    }

    private String AsJsonRequestBody(FeatureRequest requestObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(requestObject);
    }

    private void mockSaveServiceCalls(FeatureRequest requestObject) {
        when(mapper.featureEntity(requestObject)).thenReturn(FeatureFactory.feature());
        when(featureService.save(any(FeatureEntity.class), anyLong())).thenReturn(FeatureFactory.feature());
        when(mapper.featurePayload(any(FeatureEntity.class))).thenReturn(FeatureFactory.featureDetails());
    }

    private void verifyFeatureList() {
        verify(featureService,atLeastOnce()).featureList(0,5,"", 0);
        verify(mapper.featureListPayload(featureService.featureList(0, 5,"",0)));
    }

    private void mockFeatureList() {
        when(featureService.featureList(0, 5,"",1))
                .thenReturn(Collections.singletonList(FeatureFactory.feature()));

        when(mapper.featureListPayload(featureService.featureList(0, 5,"",1)))
                .thenReturn(Collections.singletonList(FeatureFactory.featureDetails()));
    }
}
