package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.model.FeatureType;
import com.publicissapient.anoroc.payload.request.FeatureRequest;
import com.publicissapient.anoroc.payload.response.FeatureResponse;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.publicissapient.anoroc.factory.ApplicationFactoryTest.getApplication;

@ActiveProfiles("test")
public class FeatureFactory {

    public FeatureFactory() {
    }

    public static FeatureEntity feature() {
        return FeatureEntity.builder().id(1l).name("Sample Feture")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content("content")
                .featureType(FeatureType.ANOROC)
                .xPath("Feature Content")
                .runs(new ArrayList<>())
                .businessScenarios(new ArrayList<>())
                .application(getApplication(1l))
                .build();


    }

    public static FeatureResponse featureDetails() {
        return FeatureResponse.builder().id(1L).name("Sample feature")
                .createdDateTime(LocalDateTime.now())
                .content("Feature Content").xpath(null).featureType(FeatureType.ANOROC).build();
    }

    public static FeatureEntity feature(long id) {
        return FeatureEntity.builder().id(id).name("Sample Feture")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content(null)
                .featureType(FeatureType.ANOROC)
                .runs(new ArrayList<>())
                .includeFeatures(new ArrayList<>())
                .businessScenarios(new ArrayList<>())
                .xPath("{\n  \"test\":\"132\"\n}")
                .application(getApplication(1l))
                .build();
    }

    public static FeatureRequest featureRequest() {
        return FeatureRequest.builder().name("Simple Login Feature").content("Scenario Content").xPath("").featureType(FeatureType.ANOROC).applicationId(1l).build();
    }

    public static FeatureRequest featureRequest(String featureName, long featureId) {
        return FeatureRequest.builder().id(featureId).name("Simple Login Feature").content("Scenario Content").xPath("").featureType(FeatureType.ANOROC).applicationId(1l).build();

    }

}
