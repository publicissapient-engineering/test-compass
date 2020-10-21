package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.FeatureNotFoundException;
import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.repository.ApplicationRepository;
import com.publicissapient.anoroc.repository.FeatureRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureService extends  AnorocService{

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public long count(String name, long id) {
        return (StringUtils.isEmpty(name) && id == 0) ? totalCount() : countByNameAndIdNotIn(name, id);
    }

    public long totalCount() {
        return featureRepository.count();
    }

    public long countByNameAndIdNotIn(String name, long id) {
        return featureRepository.countByNameAndIdNotIn(name, id);
    }

    public List<FeatureEntity> featureList(Integer page, Integer size, String searchByName, long notIn) {
        return (StringUtils.isEmpty(searchByName) && notIn == 0) ? featureList(page, size) :  includeFeatureList(page,size, searchByName, notIn);
    }

    public List<FeatureEntity> featureList(Integer page, Integer size) {
        return featureRepository.findAll(pageRequestBuilder(page, size)).getContent();
    }

    public List<FeatureEntity> includeFeatureList(Integer page, Integer size, String searchByName, long notIn) {
        return  StringUtils.isEmpty(searchByName) ? getFeaturesByNotIn(page, size, notIn) : getFeaturesByNameContainsAndIdNotIn(page, size, searchByName, notIn);
    }

    public List<FeatureEntity> associatedFeatureList(long parentFeatureId) {
        FeatureEntity featureEntity = featureById(parentFeatureId);
        return CollectionUtils.isEmpty(featureEntity.getIncludeFeatures()) ? Collections.emptyList() : getFeatures(featureEntity.getIncludeFeatures());
    }

    private List<FeatureEntity> getFeatures(List<Long> featureIds) {
        return featureIds.stream().map(id-> featureById(id)).collect(Collectors.toList());
    }

    public FeatureEntity featureById(Long featureId) throws FeatureNotFoundException {
        return featureRepository.findById(featureId)
                .orElseThrow(() -> new FeatureNotFoundException("Feature Not Found"));
    }

    public FeatureEntity featureByName(String name) {
        return  featureRepository.findByName(name);
    }

    public FeatureEntity save(FeatureEntity featureEntity, long applicationId) {
        Application application = applicationRepository.findById(applicationId).get();
        featureEntity.setApplication(application);
        return save(featureEntity);
    }

    public FeatureEntity save(FeatureEntity featureEntity) {
        return featureRepository.save(featureEntity);
    }

    public List<FeatureEntity> getFeatures(long applicationId) {
       return featureRepository.findByApplicationId(applicationId);
    }

    public List<FeatureEntity> getFeaturesByBusinessScenarioId(long businessScenarioId) {
        return featureRepository.findByBusinessScenariosId(businessScenarioId);
    }

    public long getFeaturescount(long applicationId) {
        return featureRepository.countByApplicationId(applicationId);
    }

    public List<FeatureEntity> getFeaturesByNameContainsAndIdNotIn(Integer page, Integer size, String name, long notIn) {
        return featureRepository.findByIdNotAndNameContainsIgnoreCase(notIn,name , pageRequestBuilder(page, size)).getContent();
    }

    public List<FeatureEntity> getFeaturesByNotIn(Integer page, Integer size, long notIn) {
        return featureRepository.findByIdNot(notIn, pageRequestBuilder(page, size)).getContent();
    }
}
