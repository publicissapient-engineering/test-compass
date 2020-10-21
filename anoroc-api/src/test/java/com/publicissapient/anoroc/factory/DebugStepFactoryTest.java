package com.publicissapient.anoroc.factory;

import com.publicissapient.anaroc.webdrivers.Session;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import com.publicissapient.anoroc.payload.request.DebugRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DebugStepFactoryTest {

    public static StepDefinition stepDefinition() {
        return StepDefinition.build().withInstruction("Open Url").withResult(getPassResult());
    }

    public static DebugRequest stepRequest() {
        return DebugRequest.builder().instruction("I OPEN [url]").xpath("https://www.google.com").build();
    }

    public static Result getPassResult() {
        return Result.builder().durationInNano(new Date().getTime()).status(Status.PASSED).build();
    }

    public  static Session getSession() {
        return Session.builder().uuid(UUID.randomUUID().toString()).createdAt(new Date()).build();
    }

    public static  Map<String, Object> getUriVariables() {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("sessionId", UUID.randomUUID().toString());
        return uriVariables;
    }

}
