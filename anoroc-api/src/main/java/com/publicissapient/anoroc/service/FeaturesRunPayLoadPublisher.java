package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.messaging.sender.MessageSender;
import com.publicissapient.anoroc.model.*;
import com.publicissapient.anoroc.repository.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class FeaturesRunPayLoadPublisher extends  AnorocService implements PayLoadPublisher {

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private MessageSender sender;

    @Autowired
    private RunPayLoadGeneratorService payLoadGeneratorService;

    @Override
    public boolean isRunTypeMatches(RunType runType) {
        return runType.equals(RunType.FEATURE);
    }

    @Override
    public Run saveAndPublishToQueue(PayloadPublisher request) throws Exception {
        FeatureEntity featureEntity = featureService.featureById(request.getPayloadId());
        Environment environment = environmentService.getEnv(request.getEnvironmentId());
        Run run = save(featureEntity, environment);
        sender.sendToRunInitQueue(payLoadGeneratorService.generatePayLoad(featureEntity, run));
        return run;
    }

    private Run save(FeatureEntity featureEntity, Environment environment) {
        return runRepository.save(createRun(featureEntity, environment));
    }

    private Run createRun(FeatureEntity featureEntity, Environment environment) {
        return Run.builder().details(featureEntity.getName())
                .status(RunStatus.RUNNING)
                .runType(RunType.FEATURE)
                .features(Arrays.asList(featureEntity))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .environment(environment)
                .build();
    }

}
