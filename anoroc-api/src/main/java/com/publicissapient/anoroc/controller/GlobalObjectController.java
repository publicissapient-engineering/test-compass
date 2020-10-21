package com.publicissapient.anoroc.controller;


import com.publicissapient.anoroc.exception.GlobalObjectNotFoundException;
import com.publicissapient.anoroc.model.GlobalObject;
import com.publicissapient.anoroc.payload.mapper.GlobalObjectMapper;
import com.publicissapient.anoroc.payload.request.GlobalObjectRequest;
import com.publicissapient.anoroc.payload.response.GlobalObjectCount;
import com.publicissapient.anoroc.payload.response.GlobalObjectResponse;
import com.publicissapient.anoroc.service.GlobalObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/globalObject")
public class GlobalObjectController extends AnorocBaseController{

    @Autowired
    private GlobalObjectService service;

    @Autowired
    private GlobalObjectMapper objectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<GlobalObjectResponse> getGlobalObject(@Min(0) @PathVariable long id) {
        GlobalObject globalObject = service.getById(id);
        return new ResponseEntity<>(objectMapper.globalObjectResponsePayload(globalObject), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<GlobalObjectResponse>> getList(@Min(0)@RequestParam int page, @Min(1) @RequestParam int size) {
        List<GlobalObject> globalObjects = service.getList(page, size);
        return new ResponseEntity<>(objectMapper.globalObjectResponsePayloads(globalObjects), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GlobalObjectResponse>> getAll() {
        List<GlobalObject> globalObjects = service.getAll();
        return new ResponseEntity<>(objectMapper.globalObjectResponsePayloads(globalObjects), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<GlobalObjectCount> getCount() {
        return new ResponseEntity<GlobalObjectCount>(objectMapper.globalObjectCount(service.getCount()), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<GlobalObjectResponse> save(@Valid  @RequestBody GlobalObjectRequest request) {
        GlobalObject globalObject = service.save(objectMapper.globalObjectEntity(request), request.getApplicationId());
        return new ResponseEntity<GlobalObjectResponse>(objectMapper.globalObjectResponsePayload(globalObject), getStatusCode(request.getId()));
    }

    @ExceptionHandler(value = {GlobalObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void globalObjectNotFound() {
    }

}
