package com.publicissapient.anoroc.service;


import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.factory.GlobalObjectFactoryTest;
import com.publicissapient.anoroc.factory.RunFactoryTest;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RunPayloadGeneratorServiceTest {

    @MockBean
    private GlobalObjectService globalObjectService;

    @MockBean
    private FeatureService featureService;

    @Autowired
    private RunPayLoadGeneratorService runPayLoadGeneratorService;

    @Test
    void should_be_able_to_generate_payload() throws Exception {
        FeatureEntity featureEntity = FeatureFactory.feature(1l);
        Run run = RunFactoryTest.run(1l, 1l);
        RunPayload runPayload = runPayLoadGeneratorService.generatePayLoad(featureEntity, run);
        assertThat(runPayload).isNotNull();
    }

    @Test
    void should_be_able_to_generate_payload_for_an_feature_has_include_features() throws Exception {
        FeatureEntity featureEntity = FeatureFactory.feature(1l);
        Run run = RunFactoryTest.run(1l, 1l);
        run.setFeatures(Arrays.asList(bookClickFeature()));
        when(featureService.featureById(2l)).thenReturn(mobileClickFeature());
        when(featureService.featureById(1l)).thenReturn(homePageFeature());
        when(globalObjectService.getGlobalObjectsAssociatedWithApplicationOfTheFeature(any())).thenReturn(Arrays.asList(getGlobalObject()));
        RunPayload runPayload = runPayLoadGeneratorService.generatePayLoad(bookClickFeature(), run);
        assertThat(runPayload).isNotNull();
    }

    private FeatureEntity bookClickFeature() {
        return FeatureEntity.builder().id(3l).name("Book Click Feature")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content("Feature: Amazon home page\n  Scenario: Amazon home page Scenario\n    * call read('Amazon mobile feature.feature')\n    * click('{}Books')\n    * delay(1000).screenshot()\n    ")
                .featureType(FeatureType.KARATE)
                .runs(new ArrayList<>())
                .includeFeatures(new ArrayList<>())
                .businessScenarios(new ArrayList<>())
                .xPath("{\n  \"test\":\"132\"\n}")
                .includeFeatures(Arrays.asList(2l))
                .application(getApplication(1l))
                .build();
    }

    private FeatureEntity mobileClickFeature() {
        return FeatureEntity.builder().id(2l).name("Mobile Click Feature")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content("Feature: Amazon home page\\n  Scenario: Amazon home page Scenario\\n    * call read('Amazon home page.feature')\\n    * click('{}Mobiles')\\n    * delay(1000).screenshot()\\n    ")
                .featureType(FeatureType.KARATE)
                .runs(new ArrayList<>())
                .includeFeatures(new ArrayList<>())
                .businessScenarios(new ArrayList<>())
                .xPath("{\n  \"test\":\"132\"\n}")
                .includeFeatures(Arrays.asList(1l))
                .application(getApplication(1l))
                .build();
    }

    private FeatureEntity homePageFeature() {
        return FeatureEntity.builder().id(1l).name("Home Page Feature")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content("Feature: Amazon home page\n  Scenario: Amazon home page Scenario\n    * driver url\n    * driver.maximize()\n    * delay(1000).screenshot()\n    ")
                .featureType(FeatureType.KARATE)
                .runs(new ArrayList<>())
                .includeFeatures(new ArrayList<>())
                .businessScenarios(new ArrayList<>())
                .xPath("{\n  \"test\":\"132\"\n}")
                .application(getApplication(1l))
                .build();
    }

    private Application getApplication(long id) {
        return Application.builder().id(1l).name("AMAZON")
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    private GlobalObject getGlobalObject() {
        String content = "{\n  \"test\":\"132\"\n}";
        return GlobalObjectFactoryTest.getGlobalObject(1l, content);
    }


}
