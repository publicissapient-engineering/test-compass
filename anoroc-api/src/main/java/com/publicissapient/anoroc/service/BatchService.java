package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.BatchNotFoundException;
import com.publicissapient.anoroc.model.Batch;
import com.publicissapient.anoroc.payload.request.PaginationRequest;
import com.publicissapient.anoroc.repository.BatchRepository;
import com.publicissapient.anoroc.repository.BusinessScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {

    @Autowired
    private BatchRepository repository;

    @Autowired
    private BusinessScenarioRepository businessScenarioRepository;

    public Batch save(Batch batch) {
        return repository.save(batch);
    }

    public Long count() {
        return repository.count();
    }

    public List<Batch> list(PaginationRequest pagination) {
        if(pagination.getApplicationId() != null){
           return repository.findAllByApplicationId(pagination.getApplicationId(), pageRequestBuilder(pagination.getPage(),pagination.getSize())).getContent();
        }else {
            return repository.findAll(pageRequestBuilder(pagination.getPage(), pagination.getSize())).getContent();
        }
    }

    private PageRequest pageRequestBuilder(Integer page, Integer size) {
        return PageRequest.of(page, size, Sort.by("createdAt").descending());
    }

    public Batch get(Long id) {
        return repository.findById(id).orElseThrow(()-> new BatchNotFoundException(""));
    }
}
