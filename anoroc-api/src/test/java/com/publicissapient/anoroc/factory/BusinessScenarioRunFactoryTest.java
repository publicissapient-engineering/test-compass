package com.publicissapient.anoroc.factory;


import com.publicissapient.anoroc.payload.request.BusinessScenarioRunRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class BusinessScenarioRunFactoryTest {


    public static BusinessScenarioRunRequest getBusinessScenarioRunRequest(){
        return BusinessScenarioRunRequest.builder().businessScenarioId(1l).environmentId(1l).build();
    }

}