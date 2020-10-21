package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.repository.BusinessScenarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusinessScenarioService extends AnorocService {

    @Autowired
    private BusinessScenarioRepository businessScenarioRepository;

    @Autowired
    private FeatureService featureService;


    public BusinessScenario save(BusinessScenario businessScenario, List<Long> featureIds) {
        if(isExistingOne(businessScenario)) {
            updateBusinessScenarioDetail(businessScenario);
        }
        businessScenario.setFeatures(getFeatureEntities(featureIds, businessScenario));
        return businessScenarioRepository.save(businessScenario);
    }

    private boolean isExistingOne(BusinessScenario businessScenario) {
       return (businessScenario != null && businessScenario.getId() != null && businessScenario.getId() != 0);
    }

    public void updateBusinessScenarioDetail(BusinessScenario businessScenario) {
        businessScenario.setUpdatedAt(LocalDateTime.now());
    }

    private List<FeatureEntity> getFeatureEntities(List<Long> featureIds, BusinessScenario businessScenario) {
        if(CollectionUtils.isEmpty(featureIds)) {
            log.info("Feature list is empty for the business scenario : {}", businessScenario.getName());
            return Collections.emptyList();
        }
        return featureIds.stream().map(id -> featureService.featureById(id)).collect(Collectors.toList());
    }

    public BusinessScenario getBusinessScenario(long businessScenarioId) {
        BusinessScenario businessScenario =  businessScenarioRepository.findById(businessScenarioId).orElseThrow(()-> getBusinessScenarioNotFoundException(businessScenarioId));
        businessScenario.setFeatures(getFeatures(businessScenarioId));
        return businessScenario;
    }

    public List<BusinessScenario> getBusinessScenarios(PageRequest page) {
        List<BusinessScenario> businessScenarios =  businessScenarioRepository.findAll(page).getContent();
        businessScenarios.stream().forEach(businessScenario -> setFeatures(getFeatures(businessScenario.getId()), businessScenario));
        return businessScenarios;
    }

    public List<FeatureEntity> getFeatures(long businessScenarioId) {
        return featureService.getFeaturesByBusinessScenarioId(businessScenarioId);
    }

    private void setFeatures(List<FeatureEntity> featureEntities, BusinessScenario businessScenario) {
        businessScenario.setFeatures(featureEntities);
    }

    public long getCount() {
       return businessScenarioRepository.count();
    }

    public BusinessScenario removeFeatureFromBusinessScenario(long businessScenarioId, List<Long> featuresIdsToRemove) {
        BusinessScenario businessScenario = getBusinessScenario(businessScenarioId);
        if(CollectionUtils.isEmpty(featuresIdsToRemove) || CollectionUtils.isEmpty(businessScenario.getFeatures())) {
            return businessScenario;
        }
        List<FeatureEntity> listOfFeaturesAfterRemoved = businessScenario.getFeatures().stream().filter(featureEntity -> !featuresIdsToRemove.contains(featureEntity.getId())).collect(Collectors.toList());
        businessScenario.setFeatures(listOfFeaturesAfterRemoved);
        return businessScenarioRepository.save(businessScenario);
    }

}
