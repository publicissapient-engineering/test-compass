package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.model.Batch;
import com.publicissapient.anoroc.payload.request.BatchRequest;
import com.publicissapient.anoroc.payload.response.ApplicationResponse;
import com.publicissapient.anoroc.payload.response.BatchResponse;
import com.publicissapient.anoroc.payload.response.BusinessScenarioResponse;

import java.time.LocalDateTime;
import java.util.Collections;

public class BatchFactory {

    public static BatchRequest createRequest(){
        return BatchRequest.builder()
                .name("Batch1")
                .description("Batch of Business Scenarios")
                .businessScenarios(Collections.singletonList(new Long(1)))
                .applicationId(new Long(1))
                .build();
    }

    public static Batch createEntity(){
        return Batch.builder()
                .id(1)
                .name("batch1")
                .description("Batch of Business Scenarios")
                .application(Application.builder()
                        .id(1l)
                        .name("Application 1")
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();
    }

    public static BatchResponse response() {
        return BatchResponse.builder()
                .id(1L)
                .name("Batch smoke test")
                .description("Batch smoke test description")
                .businessScenarios(Collections.singletonList(BusinessScenarioResponse.builder()
                        .id(1l)
                        .name("smoke_test")
                        .build()))
                .application(ApplicationResponse.builder()
                        .id(1l)
                        .name("Application 1")
                        .build())
                .build();
    }
}
