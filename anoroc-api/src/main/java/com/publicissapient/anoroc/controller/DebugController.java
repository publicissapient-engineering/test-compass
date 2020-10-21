package com.publicissapient.anoroc.controller;

import com.publicissapient.anaroc.exception.DebugSessionInvalidException;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import com.publicissapient.anoroc.payload.builder.StepDefinitionBuilder;
import com.publicissapient.anoroc.payload.request.DebugRequest;
import com.publicissapient.anoroc.payload.response.DebugResponse;
import com.publicissapient.anoroc.payload.response.StartDebugResponse;
import com.publicissapient.anoroc.payload.response.StopDebugResponse;
import com.publicissapient.anoroc.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.publicissapient.anoroc.payload.builder.StepDefinitionBuilder.XpathBuilder;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private DebugService debugService;

    @GetMapping("/start")
    public ResponseEntity<StartDebugResponse> startDebug() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        debugService.startDebug(sessionId);
        return new ResponseEntity<>(StartDebugResponse.builder().sessionId(sessionId).message("Debug started successfully").build(), HttpStatus.OK);
    }

    @GetMapping("/stop")
    public ResponseEntity<StopDebugResponse> stopDebug(@RequestParam(name = "sessionId") String sessionId){
        debugService.stopDebug(sessionId);
        return new ResponseEntity<>(StopDebugResponse.builder().sessionId(sessionId).message("Debug stopped successfully").build(), HttpStatus.OK);
    }

    @PostMapping("/run")
    public ResponseEntity<DebugResponse> run(@Valid @RequestBody DebugRequest debugRequest, @RequestParam(name = "sessionId") String sessionId) {
        StepDefinition stepDefinition = StepDefinitionBuilder.buildStepDef().from(debugRequest);
        Result result = debugService.execute(sessionId, stepDefinition, XpathBuilder.buildXpath(debugRequest));
        return new ResponseEntity<>(getDebugStepResponse(result), HttpStatus.OK);
    }

    private DebugResponse getDebugStepResponse(Result result) {
        return DebugResponse.builder()
                .screenShots(result.getScreenShots())
                .durationInNano(result.getDurationInNano())
                .errorMessage(result.getErrorMessage())
                .status(result.getStatus().getRawName()).build();
    }

    @ExceptionHandler(DebugSessionInvalidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void debugSessionInvalidExceptionHandler() {

    }


}
