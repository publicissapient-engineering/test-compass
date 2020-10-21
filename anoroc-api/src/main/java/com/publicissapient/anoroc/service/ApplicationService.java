package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.ApplicationNotFoundException;
import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService extends AnorocService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private FeatureService featureService;

    public Application save(Application application) {
        if(isExistingOne(application)) {
            application.setUpdatedAt(LocalDateTime.now());
        }
        return applicationRepository.save(application);
    }

    private boolean isExistingOne(Application application) {
        return (application != null && application.getId() != null && application.getId() != 0);
    }

    public Application getById(Long id) throws ApplicationNotFoundException {
        Application application =  applicationRepository.findById(id).orElseThrow(()-> new ApplicationNotFoundException("Application not found for an Id : "+id));
        application.setFeatureCount(featureService.getFeaturescount(id));
        return application;
    }

    public List<Application> getApplicationList(int page, int size) {
        List<Application> applications = applicationRepository.findAll(pageRequestBuilder(page, size)).getContent();
        applications.stream().forEach(application -> featureService.getFeatures(application.getId()));
        return  applications;

    }

    public Long getCount() {
        return applicationRepository.count();
    }

    public List<Application> getAll() {
        List<Application> applications =  applicationRepository.findAll();
        applications.stream().forEach(application -> featureService.getFeatures(application.getId()));
        return applications;
    }

}
