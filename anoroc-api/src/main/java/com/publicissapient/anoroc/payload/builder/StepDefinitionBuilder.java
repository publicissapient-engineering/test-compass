package com.publicissapient.anoroc.payload.builder;

import com.publicissapient.anoroc.model.StepDefinition;
import com.publicissapient.anoroc.payload.request.DebugRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StepDefinitionBuilder {


    private StepDefinition stepDefinition;

    private StepDefinitionBuilder() {
        stepDefinition = new StepDefinition();
    }

    public static  StepDefinitionBuilder buildStepDef() {
        return new StepDefinitionBuilder();
    }

    public  StepDefinition from(DebugRequest debugRequest) {
        this.stepDefinition.withData(getStepKey(debugRequest.getInstruction()), debugRequest.getData())
                        .withInstruction(debugRequest.getInstruction());
        return this.stepDefinition;
    }

    private static String getStepKey(String step) {
        int startIndex = 0;
        int endIndex = 0;
        if (step.contains("<") && step.contains(">")) {
            startIndex = step.indexOf("<") + 1;
            endIndex = step.indexOf(">");
        } else if (step.contains("[") && step.contains("]")) {
            startIndex = step.indexOf("[") + 1;
            endIndex = step.indexOf("]");
        }
        return step.substring(startIndex, endIndex);
    }

    public static class  XpathBuilder {
        public static  Map<String, String> buildXpath(DebugRequest debugRequest) {
            Map<String, String> xPaths = new HashMap<>();
            String stepKey = getStepKey(debugRequest.getInstruction());
            if (StringUtils.isEmpty(debugRequest.getXpath())) {
                xPaths.put(stepKey, debugRequest.getData());
            } else {
                xPaths.put(stepKey, debugRequest.getXpath());
            }
            return xPaths;
        }

    }

}
