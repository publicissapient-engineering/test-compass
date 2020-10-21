package com.publicissapient.anoroc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicissapient.anoroc.exception.*;
import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Map;


@Slf4j
public class AnorocService {

    protected PageRequest pageRequestBuilder(Integer page, Integer size) {
        return PageRequest.of(page, size, Sort.by("createdAt").descending());
    }

    protected BusinessScenarioNotFoundException getBusinessScenarioNotFoundException(long id) {
        return new BusinessScenarioNotFoundException("Business Scenario not found for an id : "+id);
    }

    protected EnvironmentNotFoundException getEnvironmentNotFoundException(long id) {
        return new EnvironmentNotFoundException("Environment not found for an id : "+id);
    }

    protected BusinessScenarioRunFeaturesNotFoundException getBusinessScenarioRunFeaturesNotFoundException(long businessScenarioRunId){
        return new BusinessScenarioRunFeaturesNotFoundException("BusinessScenarioRunFeatures not found for an business scenario run id"+businessScenarioRunId);
    }

    protected BusinessScenarioRunNotFoundException getBusinessScenarioRunFoundException(long businessScenarioRunId){
        return new BusinessScenarioRunNotFoundException("BusinessScenarioRun not found for an business scenario run id"+businessScenarioRunId);
    }

    protected RunNotFoundException getRunNotFoundException(long id) {
        return new RunNotFoundException("Run features not found for an id : "+id);
    }

    protected Map<String, Object> getJsonMap(String jsonString) throws Exception {
        return StringUtils.isEmpty(jsonString) ? Collections.emptyMap() : new ObjectMapper().readValue(jsonString, Map.class);
    }

    protected  GlobalObjectNotFoundException getGlobalObjectNotFoundException(long id){
        return new GlobalObjectNotFoundException("Global Object not found for an id : "+id);
    }

    protected BusinessScenarioRunPayload createBusinessScenarioRunPayLoad(long businessScenarioId, long runId, long envId) {
        return BusinessScenarioRunPayload.builder()
                .runId(runId)
                .environmentId(envId)
                .businessScenarioId(businessScenarioId)
                .build();
    }

}
