package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.payload.mapper.BatchModelMapper;
import com.publicissapient.anoroc.payload.request.BatchRequest;
import com.publicissapient.anoroc.payload.request.PaginationRequest;
import com.publicissapient.anoroc.payload.response.BatchCount;
import com.publicissapient.anoroc.payload.response.BatchResponse;
import com.publicissapient.anoroc.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @Autowired
    private BatchModelMapper mapper;

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public BatchResponse saveBatch(@Valid @RequestBody BatchRequest requestBody){
        return mapper.response(batchService.save(mapper.entity(requestBody)));
    }

    @GetMapping("/count")
    public BatchCount count(){
        return BatchCount.builder().count(batchService.count()).build();
    }

    @GetMapping("/list")
    public List<BatchResponse> getList(@Valid PaginationRequest pagination){
        return mapper.responseList(batchService.list(pagination));
    }

    @GetMapping("/{id}")
    public BatchResponse getBatch(@Min(1) @PathVariable long id){
        return mapper.response(batchService.get(id));
    }
}
