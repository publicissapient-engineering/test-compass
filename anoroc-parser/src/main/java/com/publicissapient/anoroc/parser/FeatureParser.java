package com.publicissapient.anoroc.parser;

import com.publicissapient.anoroc.exception.FeatureFileNotFoundException;
import com.publicissapient.anoroc.model.Feature;
import io.cucumber.gherkin.Gherkin;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages.Envelope;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class FeatureParser {

    public static final boolean DO_NOT_INCLUDE_SOURCE = false;
    public static final boolean INCLUDE_AST = true;
    public static final boolean DO_NOT_INCLUDE_PICKLES = false;

    public List<Feature> features(String filePath) {
        List<Envelope> envelopes = parseFile(filePath);
        return envelopes
                .stream()
                .map(this::buildFeature)
                .collect(Collectors.toList());
    }

    public List<Feature> features(String featureContent,String name){
       List<Envelope> envelopes = parseContent(featureContent,name);
        return envelopes
                .stream()
                .map(this::buildFeature)
                .collect(Collectors.toList());
    }

    private List<Envelope> parseContent(String featureContent, String name) {
        Envelope envelope = Gherkin.makeSourceEnvelope(featureContent,name);
       return Gherkin.fromSources(singletonList(envelope), false, true, false, new IdGenerator.Incrementing()).collect(Collectors.toList());

    }

    private Feature buildFeature(Envelope envelope) {
        Feature feature = Feature
                .build(envelope.getGherkinDocument().getFeature().getName());
        ScenarioOutlineParser.build().parseAndUpdateScenarioOutlines(feature, envelope);
        return feature;
    }

    private List<Envelope> parseFile(String filePath) {
        try {
            return Gherkin.fromPaths(singletonList(
                    filePath),
                    DO_NOT_INCLUDE_SOURCE,
                    INCLUDE_AST,
                    DO_NOT_INCLUDE_PICKLES,
                    new IdGenerator.Incrementing())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new FeatureFileNotFoundException(new Exception("File not found."));
        }
    }
}
