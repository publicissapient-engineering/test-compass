package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RunPayLoadGeneratorService extends  AnorocService{

    @Autowired
    private GlobalObjectService globalObjectService;

    @Autowired
    private FeatureService featureService;

    public RunPayload generatePayLoad(FeatureEntity featureEntity, Run run) throws Exception {
        RunPayload runPayload = getPayLoad(featureEntity, run);
        runPayload.setIncludeFeatureRunPayload(getIncludeRunPayloads(featureEntity.getIncludeFeatures(), run));
        List<GlobalObject> getGlobalObjects = getGlobalObjects(featureEntity);
        runPayload.setArgs(populateArgs(run, runPayload, getGlobalObjects, featureEntity.getFeatureType()));
        return runPayload;
    }

    public RunPayload getPayLoad(FeatureEntity featureEntity, Run run) throws Exception {
        return RunPayload.builder()
                .name(featureEntity.getName())
                .featureId(featureEntity.getId())
                .featureType(featureEntity.getFeatureType())
                .runId(run.getId())
                .content(featureEntity.getContent())
                .status(RunStatus.RUNNING)
                .environment(run.getEnvironment().getName())
                .args(new HashMap<>())
                .includeFeatureRunPayload(new ArrayList<>())
                .applicationName(featureEntity.getApplication().getName())
                .xpath(featureEntity.getXPath()).build();
    }

    public List<RunPayload> getIncludeRunPayloads(List<Long> includeFeatures, Run run) throws  Exception{
        if(CollectionUtils.isEmpty(includeFeatures)) {
            return Collections.emptyList();
        }
        List<RunPayload> includeRunPayloads = new ArrayList<>();
        for(long featureId: includeFeatures) {
            FeatureEntity includeFeature = getIncludeFeature(featureId);
            RunPayload includeRunPayload = getPayLoad(includeFeature, run);
            populateXpathArgs(includeFeature, includeRunPayload);
            //recursive call
            List<RunPayload> nestedPayloads = getIncludeRunPayloads(includeFeature.getIncludeFeatures(), run);
            includeRunPayload.setIncludeFeatureRunPayload(nestedPayloads);
            includeRunPayloads.add(includeRunPayload);
        }
        return includeRunPayloads;
    }


    protected  List<GlobalObject> getGlobalObjects(FeatureEntity featureEntity) throws Exception {
        List<GlobalObject> globalObjects = new ArrayList<>();
        globalObjects.addAll(getGlobalObjectsAssociatedWithAnApplication(featureEntity));
        globalObjects.addAll(getIncludeGlobalObjects(featureEntity.getIncludeFeatures()));
        return globalObjects;
    }


    public List<GlobalObject> getIncludeGlobalObjects(List<Long> includeFeatures) throws  Exception{
        if(CollectionUtils.isEmpty(includeFeatures)) {
            return Collections.emptyList();
        }
        List<GlobalObject> globalObjects = new ArrayList<>();
        for(long featureId: includeFeatures) {
            FeatureEntity includeFeature = getIncludeFeature(featureId);
            // get the global object assocaited with include feature
            globalObjects.addAll(getGlobalObjectsAssociatedWithAnApplication(includeFeature));
            //recursive call
            globalObjects.addAll(getIncludeGlobalObjects(includeFeature.getIncludeFeatures()));
        }
        return globalObjects;
    }

    private FeatureEntity getIncludeFeature(long featureId) {
        return featureService.featureById(featureId);
    }

    protected List<GlobalObject> getGlobalObjectsAssociatedWithAnApplication(FeatureEntity entity) {
        return globalObjectService.getGlobalObjectsAssociatedWithApplicationOfTheFeature(entity);
    }

    protected  Map<String, Object> populateArgs(Run run, RunPayload runPayload, List<GlobalObject> globalObjects, FeatureType featureType) throws  Exception{
        Map<String, Object> args = new HashMap<>();
        populateGlobalObjectArgs(args, new HashSet<>(globalObjects));
        populateEnvArgs(run, args, featureType);
        args.putAll(populateXpathArgs(runPayload));
        return args;
    }

    protected void populateXpathArgs(FeatureEntity featureEntity, RunPayload runPayload) throws Exception {
        Map<String, Object> xpathMap = new HashMap<>();
        xpathMap.putAll(getJsonMap(featureEntity.getXPath()));
        runPayload.setArgs(xpathMap);
    }

    protected Map<String, Object> populateXpathArgs(RunPayload runPayload) throws Exception {
        Map<String, Object> xpathMap = new HashMap<>();
        if(CollectionUtils.isEmpty(runPayload.getIncludeFeatureRunPayload())) {
            xpathMap.putAll(getJsonMap(runPayload.getXpath()));
            return xpathMap;
        }
        for(RunPayload includeRunPayload : runPayload.getIncludeFeatureRunPayload()) {
            xpathMap.putAll(populateXpathArgs(includeRunPayload));
        }
        xpathMap.putAll(getJsonMap(runPayload.getXpath()));
        return xpathMap;
    }



    private void populateEnvArgs(Run run, Map<String, Object> xpathMap, FeatureType featureType) throws Exception {
        if(featureType == FeatureType.ANOROC) {
            xpathMap.putAll(getJsonMap(run.getEnvironment().getAnorocContent()));
        } else {
            xpathMap.putAll(getJsonMap(run.getEnvironment().getKarateContent()));
        }
        xpathMap.putAll(getJsonMap(run.getEnvironment().getSettingsContent()));
    }

    private void populateGlobalObjectArgs(Map<String, Object> xpathMap, Set<GlobalObject> globalObjects) throws Exception {
        globalObjects.stream().filter(globalObject -> StringUtils.isNotEmpty(globalObject.getContent())).forEach(globalObject -> {
            try {
                xpathMap.putAll(getJsonMap(globalObject.getContent()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
