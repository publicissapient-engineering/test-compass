package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.payload.request.BusinessScenarioRequest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessScenarioFactoryTest extends  AnorocFactoryTest{

    public static BusinessScenarioRequest getBusinessScenarioRequest() {
        return BusinessScenarioRequest.builder().name("smoke").featureIds(Arrays.asList(1l,2l,3l)).build();
    }

    public static BusinessScenario getBusinessScenarioEntity(long id, List<Long> featureIds) {
        BusinessScenario businessScenario =  BusinessScenario.builder().id(id).name("load_test").description("smoke_test_desc").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
        businessScenario.setFeatures(getFeatures(featureIds, businessScenario));
        return businessScenario;
    }

    public static  List<FeatureEntity> getFeatures(List<Long> featureIds, BusinessScenario businessScenario) {
        if(CollectionUtils.isEmpty(featureIds)) {
            return Collections.emptyList();
        }
        return featureIds.stream().map(id -> FeatureEntity.builder().id(id).name("Feature1")
                .createdAt(LocalDateTime.now())
                .application(ApplicationFactoryTest.getApplication(1l))
                .includeFeatures(new ArrayList<>())
                .updatedAt(LocalDateTime.now()).build()).collect(Collectors.toList());
    }

    public static  BusinessScenarioRequest getBusinessScenarioPayLoad(){
        return BusinessScenarioRequest.builder().name("business_scenario").description("business_scenario_Desc").featureIds(Arrays.asList(1l)).build();
    }

    public static  BusinessScenarioRequest getBusinessScenarioPayLoad(List<Long> featureIds){
        return BusinessScenarioRequest.builder().name("business_scenario").description("business_scenario_Desc").featureIds(featureIds).build();
    }

    public static  BusinessScenarioRequest getBusinessScenarioPayLoad(long id){
        return BusinessScenarioRequest.builder().id(id).name("business_scenario").description("business_scenario_Desc").featureIds(Arrays.asList(1l)).build();
    }

    public static  BusinessScenarioRequest getBusinessScenarioPayLoad(long businessScenarioId, long removeFeatureId){
        return BusinessScenarioRequest.builder().id(businessScenarioId).name("business_scenario").description("business_scenario_Desc").featureIds(Arrays.asList(removeFeatureId)).build();
    }

    public static  BusinessScenarioRequest getBusinessScenarioPayLoadWithoutFeatureIds(){
        return BusinessScenarioRequest.builder().name("business_scenario").description("business_scenario_Desc").build();
    }

}
