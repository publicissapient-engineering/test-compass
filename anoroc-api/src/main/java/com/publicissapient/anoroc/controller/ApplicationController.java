package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.ApplicationNotFoundException;
import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.payload.mapper.ApplicationModelMapper;
import com.publicissapient.anoroc.payload.request.ApplicationRequest;
import com.publicissapient.anoroc.payload.response.ApplicationCount;
import com.publicissapient.anoroc.payload.response.ApplicationResponse;
import com.publicissapient.anoroc.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController extends AnorocBaseController{

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<ApplicationResponse> save(@Valid  @RequestBody ApplicationRequest applicationRequest) {
        Application applicationEntity = modelMapper.applicationEntity(applicationRequest);
        Application savedEntity = applicationService.save(applicationEntity);
        return new ResponseEntity<ApplicationResponse>(modelMapper.applicationResponsePayload(savedEntity), getStatusCode(applicationRequest.getId()));
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponse> get(@Min(1) @PathVariable long applicationId) {
        Application entity = applicationService.getById(applicationId);
        return new ResponseEntity<ApplicationResponse>(modelMapper.applicationResponsePayload(entity), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ApplicationResponse>> get(@RequestParam("page") int page, @RequestParam("size") int size){
        List<Application> entities = applicationService.getApplicationList(page, size);
        return new ResponseEntity<List<ApplicationResponse>>(modelMapper.applicationResponsePayloads(entities), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResponse>> get(){
        List<Application> entities = applicationService.getAll();
        return new ResponseEntity<List<ApplicationResponse>>(modelMapper.applicationResponsePayloads(entities), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<ApplicationCount> count() {
        long count = applicationService.getCount();
        return new ResponseEntity<>(ApplicationCount.builder().count(count).build(), HttpStatus.OK);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void applicationNotFound() {
    }

}
