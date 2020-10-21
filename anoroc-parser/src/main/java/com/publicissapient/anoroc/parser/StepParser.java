package com.publicissapient.anoroc.parser;

import com.publicissapient.anoroc.model.StepDefinition;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow;

import java.util.List;

public class StepParser {

    private StepParser() {
    }

    public static StepParser build() {
        return new StepParser();
    }

    public void parseAndUpdateStepData(
            StepDefinition stepDefinition,
            List<String> headers,
            TableRow dataRow,
            Step stepInEnvelope) {
        int headerIndex = 0;
        for (String header : headers) {
            if (stepInEnvelope.getText().contains("<" + header + ">")) {
                stepDefinition.withData(header, dataRow.getCells(headerIndex).getValue());
            }
            headerIndex++;
        }
    }
}
