package com.publicissapient.anoroc.parser;

import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.model.ScenarioOutline;
import com.publicissapient.anoroc.model.StepDefinition;
import io.cucumber.messages.Messages;
import io.cucumber.messages.Messages.Envelope;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow.getDefaultInstance;

public class ScenarioOutlineParser {

    private ScenarioOutlineParser() {
    }

    public static ScenarioOutlineParser build() {
        return new ScenarioOutlineParser();
    }

    public void parseAndUpdateScenarioOutlines(Feature feature, Envelope envelope) {
        List<Scenario> scenariosInEnvelope = envelope.getGherkinDocument()
                .getFeature().getChildrenList().stream()
                .map(Messages.GherkinDocument.Feature.FeatureChild::getScenario)
                .collect(Collectors.toList());

        scenariosInEnvelope.forEach(scenarioInEnvelope -> parseAndUpdateScenarioOutline(feature, scenarioInEnvelope));
    }

    private void parseAndUpdateScenarioOutline(Feature feature, Scenario scenarioInEnvelope) {
        List<String> headers = parseHeader(scenarioInEnvelope);
        List<TableRow> dataRows = parseDataRows(scenarioInEnvelope);

        if (dataRows.isEmpty()) {
            parseAndUpdateSingleScenarioOutline(feature, scenarioInEnvelope, headers, getDefaultInstance());
        } else {
            buildMultipleScenarioOutlines(feature, scenarioInEnvelope, headers, dataRows);
        }
    }

    private void parseAndUpdateSingleScenarioOutline(Feature feature, Scenario scenarioInEnvelope,
                                                     List<String> headers,
                                                     TableRow defaultInstance) {
        ScenarioOutline scenarioOutline = parseStepDefinition(scenarioInEnvelope, headers, defaultInstance);
        feature.getScenarioOutlines().add(scenarioOutline);
    }

    private void buildMultipleScenarioOutlines(Feature feature,
                                               Scenario scenarioInEnvelope,
                                               List<String> headers,
                                               List<TableRow> dataRows) {
        for (TableRow dataRow : dataRows) {
            parseAndUpdateSingleScenarioOutline(feature, scenarioInEnvelope, headers, dataRow);
        }
    }

    private List<String> parseHeader(Scenario scenarioInEnvelope) {
        List<String> headers = new ArrayList<>();
        scenarioInEnvelope.getExamplesList().forEach(example -> headers.addAll(example.getTableHeader()
                .getCellsList().stream()
                .map(TableRow.TableCell::getValue)
                .collect(Collectors.toList())));
        return headers;
    }

    private List<TableRow> parseDataRows(Scenario scenarioInEnvelope) {
        List<TableRow> dataRows = new ArrayList<>();
        scenarioInEnvelope.getExamplesList().forEach(example -> dataRows.addAll(example.getTableBodyList()));
        return dataRows;
    }

    private ScenarioOutline parseStepDefinition(Scenario scenarioInEnvelope,
                                                List<String> headers,
                                                TableRow dataRow) {
        ScenarioOutline scenarioOutline = ScenarioOutline
                .build(scenarioInEnvelope.getName());
        List<Messages.GherkinDocument.Feature.Step> stepsInEnvelope = scenarioInEnvelope.getStepsList();
        stepsInEnvelope.forEach(stepInEnvelope -> {
                    StepDefinition stepDefinition = StepDefinition
                            .build()
                            .withKeyword(stepInEnvelope.getKeyword())
                            .withInstruction(stepInEnvelope.getText());
                    StepParser.build().parseAndUpdateStepData(stepDefinition, headers, dataRow, stepInEnvelope);
                    scenarioOutline.addStepDefinition(stepDefinition);
                }
        );
        return scenarioOutline;
    }

}
