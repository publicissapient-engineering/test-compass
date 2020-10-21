package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.exception.BusinessScenarioNotFoundException;
import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.payload.mapper.BusinessScenarioModelMapper;
import com.publicissapient.anoroc.payload.request.BusinessScenarioRequest;
import com.publicissapient.anoroc.payload.response.BusinessScenarioCount;
import com.publicissapient.anoroc.payload.response.BusinessScenarioResponse;
import com.publicissapient.anoroc.service.BusinessScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/businessScenario")
public class BusinessScenarioController extends  AnorocBaseController{


    @Autowired
    private BusinessScenarioService businessScenarioService;

    @Autowired
    private BusinessScenarioModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<BusinessScenarioResponse> save(@Valid @RequestBody BusinessScenarioRequest businessScenarioRequest) {
        BusinessScenario businessScenario = modelMapper.businessScenario(businessScenarioRequest);
        businessScenario = businessScenarioService.save(businessScenario, businessScenarioRequest.getFeatureIds());
            return new ResponseEntity<BusinessScenarioResponse>(modelMapper.businessScenarioResponsePayload(businessScenario), getStatusCode(businessScenarioRequest.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessScenarioResponse> getBusinessScenarioById(@Min(1) @PathVariable long id) {
        BusinessScenario businessScenario = businessScenarioService.getBusinessScenario(id);
        return new ResponseEntity<BusinessScenarioResponse>(modelMapper.businessScenarioResponsePayload(businessScenario), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BusinessScenarioResponse>> getBusinessScenarios(@RequestParam int page, @RequestParam int size) {
        List<BusinessScenario> businessScenarios = businessScenarioService.getBusinessScenarios(pageRequestBuilder(page, size));
        return new ResponseEntity<List<BusinessScenarioResponse>>(modelMapper.businessScenarioResponsePayLoads(businessScenarios), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<BusinessScenarioCount> getCount() {
        long count = businessScenarioService.getCount();
        return new ResponseEntity<BusinessScenarioCount>(BusinessScenarioCount.builder().count(count).build(), HttpStatus.OK);
    }

    @PostMapping("/feature/remove")
    public ResponseEntity<BusinessScenarioResponse> removeFeature(@Valid @RequestBody BusinessScenarioRequest businessScenarioRequest) {
        BusinessScenario businessScenario =  businessScenarioService.removeFeatureFromBusinessScenario(businessScenarioRequest.getId(), businessScenarioRequest.getFeatureIds());
        return new ResponseEntity<BusinessScenarioResponse>(modelMapper.businessScenarioResponsePayload(businessScenario), HttpStatus.OK);
    }

    @ExceptionHandler(BusinessScenarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void applicationNotFound() {
    }


}
