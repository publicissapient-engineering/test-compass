package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.FeatureNotFoundException;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.payload.mapper.FeatureModelMapper;
import com.publicissapient.anoroc.payload.request.FeatureRequest;
import com.publicissapient.anoroc.payload.request.IncludePaginationRequest;
import com.publicissapient.anoroc.payload.request.PaginationRequest;
import com.publicissapient.anoroc.payload.response.FeatureCount;
import com.publicissapient.anoroc.payload.response.FeatureResponse;
import com.publicissapient.anoroc.service.FeatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/feature")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureModelMapper featureModelMapper;

    @GetMapping(value = "/count")
    public FeatureCount getCount(@RequestParam String contains, @RequestParam long notIn) {
        return new FeatureCount(featureService.count(contains, notIn));
    }

    @GetMapping(value = "/list")
    public List<FeatureResponse> getFeatureList(@Valid PaginationRequest pagination) {
        return featureListPayload(featureService.featureList(pagination.getPage(), pagination.getSize(), pagination.getName(), pagination.getNotIn()));
    }

    @GetMapping(value = "/associatedList/{id}")
    public List<FeatureResponse> getIncludeFeatureList(@Min(1) @PathVariable long id) {
        return featureListPayload(featureService.associatedFeatureList(id));
    }

    @GetMapping(value = "/{featureId}")
    public FeatureResponse getFeature(@PathVariable @Min(1) Long featureId) {
        return featurePayload(featureService.featureById(featureId));
    }

    @PostMapping(value = "/save")
    public FeatureResponse saveFeature(@Valid @RequestBody FeatureRequest requestBody) throws IOException {
        return featurePayload(featureService.save(featureEntity(requestBody), requestBody.getApplicationId()));
    }

    @ExceptionHandler(value = FeatureNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void featureNotFound(FeatureNotFoundException e) {
        log.error(e.getMessage());
    }

    private FeatureResponse featurePayload(FeatureEntity featureEntity) {
        return featureModelMapper.featurePayload(featureEntity);
    }

    private List<FeatureResponse> featureListPayload(List<FeatureEntity> featureEntityList) {
        return featureModelMapper.featureListPayload(featureEntityList);
    }

    private FeatureEntity featureEntity(FeatureRequest requestBody) {
        FeatureEntity featureEntity = featureModelMapper.featureEntity(requestBody);
        if (featureEntity.getId() != null && featureEntity.getId() != 0) {
            updateExisting(featureEntity);
        }
        return featureEntity;
    }

    private void updateExisting(FeatureEntity featureEntity) {
        FeatureEntity existingFeatureEntity = featureService.featureById(featureEntity.getId());
        if (existingFeatureEntity != null) {
            featureEntity.setCreatedAt(existingFeatureEntity.getCreatedAt());
            featureEntity.setUpdatedAt(LocalDateTime.now());
        }
    }
}
