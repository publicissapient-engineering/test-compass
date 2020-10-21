package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.RunNotFoundException;
import com.publicissapient.anoroc.exception.RunTypeNotFoundException;
import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.model.RunType;
import com.publicissapient.anoroc.payload.mapper.RunModelMapper;
import com.publicissapient.anoroc.payload.request.BusinessScenarioRunRequest;
import com.publicissapient.anoroc.payload.request.FeatureRunRequest;
import com.publicissapient.anoroc.payload.request.PaginationRequest;
import com.publicissapient.anoroc.payload.response.RunCount;
import com.publicissapient.anoroc.payload.response.RunDetail;
import com.publicissapient.anoroc.service.RunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/run")
public class RunController extends AnorocBaseController {

    @Autowired
    private RunService runService;

    @Autowired
    private RunModelMapper runModelMapper;

    @GetMapping("/count")
    public RunCount getCount() {
        return new RunCount(runService.getCount());
    }

    @GetMapping("/list")
    public List<RunDetail> runs(@Valid PaginationRequest pagination) {
        return runModelMapper.runs(runService.runs(pagination.getPage(), pagination.getSize()));
    }

    @PostMapping("/feature")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RunDetail> runFeature(@Valid @RequestBody FeatureRunRequest runRequest) throws Exception, RunTypeNotFoundException {
        PayloadPublisher payloadPublisher = getPayloadRequest(runRequest.getFeatureId(), runRequest.getEnvironmentId(), runRequest.getGlobalObjectId(), RunType.FEATURE);
        return new ResponseEntity<RunDetail>(runModelMapper.runDetails(runService.run(payloadPublisher)), HttpStatus.ACCEPTED);
    }

    @PostMapping("/businessScenario")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RunDetail> runBusinessScenario(@Valid @RequestBody BusinessScenarioRunRequest runRequest) throws Exception, RunTypeNotFoundException {
        PayloadPublisher payloadPublisher = getPayloadRequest(runRequest.getBusinessScenarioId(), runRequest.getEnvironmentId(), runRequest.getGlobalObjectId(), RunType.BUSINESS_SCENARIO);
        return new ResponseEntity<RunDetail>(runModelMapper.runDetails(runService.run(payloadPublisher)), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(value = {RunNotFoundException.class, RunTypeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void RunAndRunTypeMatchNotFound() {
    }


}
