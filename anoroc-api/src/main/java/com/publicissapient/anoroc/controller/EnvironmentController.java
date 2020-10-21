package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.EnvironmentNotFoundException;
import com.publicissapient.anoroc.model.Environment;
import com.publicissapient.anoroc.payload.mapper.EnvironmentModelMapper;
import com.publicissapient.anoroc.payload.request.EnvironmentRequest;
import com.publicissapient.anoroc.payload.response.EnvironmentCount;
import com.publicissapient.anoroc.payload.response.EnvironmentResponse;
import com.publicissapient.anoroc.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/environment")
public class EnvironmentController extends  AnorocBaseController{

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private EnvironmentModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentResponse> getEnv(@Min (1) @PathVariable long id) {
        Environment environment = environmentService.getEnv(id);
        return new ResponseEntity<>(modelMapper.getResponsePayload(environment), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<EnvironmentResponse> saveEnv(@RequestBody EnvironmentRequest environmentRequest) {
        Environment environment = modelMapper.getEnvEntity(environmentRequest);
        Environment savedEntity = environmentService.save(environment);
        return new ResponseEntity<>(modelMapper.getResponsePayload(savedEntity), getStatusCode(environmentRequest.getId()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<EnvironmentResponse>> getList( @Min(0) @RequestParam int page,  @Min(1) @RequestParam int size) {
        List<EnvironmentResponse> environmentResponse = modelMapper.getResponsePayloads(environmentService.getEnvList(page, size));
        return new ResponseEntity<>(environmentResponse, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<EnvironmentCount> count() {
        long count = environmentService.getCount();
        return new ResponseEntity<>(EnvironmentCount.builder().count(count).build(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnvironmentResponse>> getAll() {
        List<EnvironmentResponse> environmentResponse = modelMapper.getResponsePayloads(environmentService.getAll());
        return new ResponseEntity<>(environmentResponse, HttpStatus.OK);
    }

    @ExceptionHandler(EnvironmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void ExceptionHandler() {

    }
}
