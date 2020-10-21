package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.*;
import com.publicissapient.anoroc.repository.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessScenarioRunPayLoadPublisher extends AnorocService implements PayLoadPublisher {

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private BusinessScenarioService businessScenarioService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private MessageSender sender;


    @Override
    public Run saveAndPublishToQueue(PayloadPublisher request) throws Exception {
        Environment environment = environmentService.getEnv(request.getEnvironmentId());
        BusinessScenario businessScenario = businessScenarioService.getBusinessScenario(request.getPayloadId());
        Run run =  save(businessScenario, environment);
        sender.sendToBusinessScenarioRunInitQueue(createBusinessScenarioRunPayLoad(businessScenario.getId(), run.getId(), request.getEnvironmentId()));
        return run;
    }

    public Run save(BusinessScenario businessScenario, Environment environment) {
        Run run = createRun(businessScenario, environment);
        return runRepository.save(run);
    }

    public Run createRun(BusinessScenario businessScenario, Environment environment) {
        return Run.builder().status(RunStatus.RUNNING)
                .details(businessScenario.getName())
                .runType(RunType.BUSINESS_SCENARIO)
                .businessScenarios(Arrays.asList(businessScenario))
                .features(cloneFeatureEntity(businessScenario.getFeatures()))
                .updatedAt(LocalDateTime.now())
                .environment(environment)
                .createdAt(LocalDateTime.now()).build();
    }

    @Override
    public boolean isRunTypeMatches(RunType runType) {
        return runType.equals(RunType.BUSINESS_SCENARIO);
    }


    private List<FeatureEntity> cloneFeatureEntity(List<FeatureEntity> featureEntities) {
        if(CollectionUtils.isEmpty(featureEntities)) {
            return Collections.EMPTY_LIST;
        }

        return featureEntities.stream().map(featureEntity -> {
            featureEntity.setBusinessScenarios(null);
            return featureEntity;
        }).collect(Collectors.toList());
    }

}
