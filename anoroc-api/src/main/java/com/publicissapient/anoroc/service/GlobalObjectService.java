package com.publicissapient.anoroc.service;


import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.model.GlobalObject;
import com.publicissapient.anoroc.repository.GlobalObjectRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlobalObjectService extends AnorocService {

    @Autowired
    private GlobalObjectRepository globalObjectRepository;

    @Autowired
    private ApplicationService applicationService;

    public GlobalObject getById(long id) {
        return globalObjectRepository.findById(id).orElseThrow(() -> getGlobalObjectNotFoundException(id));
    }

    public List<GlobalObject> getList(int page, int size) {
        return globalObjectRepository.findAll(pageRequestBuilder(page, size)).getContent();
    }

    public long getCount() {
        return globalObjectRepository.count();
    }

    public List<GlobalObject> getAll() {
        return globalObjectRepository.findAll();
    }

    public List<GlobalObject> getByApplication(long applicationId) {
        return globalObjectRepository.findByApplicationId(applicationId);
    }

    public GlobalObject save(GlobalObject globalObject, long applicationId) {
        Application application = applicationService.getById(applicationId);
        if (isExistingOne(globalObject)) {
            updateGlobalObject(globalObject, application);
        }
        return globalObjectRepository.save(globalObject);
    }

    private boolean isExistingOne(GlobalObject globalObject) {
        return (globalObject != null && globalObject.getId() != null && globalObject.getId() > 0);
    }

    public void updateGlobalObject(GlobalObject globalObject, Application application) {
        globalObject.setUpdatedAt(LocalDateTime.now());
        globalObject.setApplication(application);
    }

    public Set<GlobalObject> getGlobalObjectsAssociatedWithApplication(List<FeatureEntity> includeFeatures , FeatureEntity parentFeature) {
        Set<GlobalObject> globalObjectSet = new HashSet<>();
        List<GlobalObject> parentGlobalObjects = (null == parentFeature ? Collections.EMPTY_LIST : getGlobalObjectByApplication(parentFeature.getApplication()));
        Set<GlobalObject> includeGlobalObjects = CollectionUtils.isEmpty(includeFeatures) ? Collections.emptySet() : includeFeatures.stream()
                                                                                                                    .map(feature -> feature.getApplication())
                                                                                                                    .map(this::getGlobalObjectByApplication)
                                                                                                                    .flatMap(globalObjectList -> globalObjectList.stream())
                                                                                                                    .collect(Collectors.toSet());
        globalObjectSet.addAll(parentGlobalObjects);
        globalObjectSet.addAll(includeGlobalObjects);
        return globalObjectSet;
    }

    public List<GlobalObject> getGlobalObjectsAssociatedWithApplicationOfTheFeature(FeatureEntity featureEntity) {
        return (null == featureEntity ? Collections.EMPTY_LIST : getGlobalObjectByApplication(featureEntity.getApplication()));
    }

    public List<GlobalObject> getGlobalObjectByApplication(Application application) {
        return getByApplication(application.getId());
    }


}
