package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.model.Environment;
import com.publicissapient.anoroc.repository.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvironmentService extends AnorocService {

    @Autowired
    private EnvironmentRepository environmentRepository;

    public Environment save(Environment environment) {
        if(isExistingOne(environment)) {
            environment.setUpdatedAt(LocalDateTime.now());
        }
        return environmentRepository.save(environment);
    }

    private boolean isExistingOne(Environment environment) {
        return (environment.getId() != null && environment.getId() != 0);
    }

    public Environment getEnv(long id) {
        return environmentRepository.findById(id).orElseThrow(()-> getEnvironmentNotFoundException(id));
    }

    public List<Environment> getEnvList(int page, int size) {
        return environmentRepository.findAll(pageRequestBuilder(page, size)).getContent();
    }

    public List<Environment> getAll() {
        return environmentRepository.findAll();
    }

    public long getCount() {
        return environmentRepository.count();
    }
}
