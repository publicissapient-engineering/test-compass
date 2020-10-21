package com.publicissapient.anoroc.model;

import lombok.Data;
import java.util.*;

@Data
public class StepDefinition {

    private String keyword;

    private String instruction;

    private Map<String, String> data = new HashMap<>();

    private Result result;


    private List<Embedded> embeddings = new ArrayList<>();

    public static StepDefinition build() {
        return new StepDefinition();
    }

    public StepDefinition withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public StepDefinition withInstruction(String instruction) {
        this.instruction = instruction;
        return this;
    }

    public StepDefinition withData(String key, String value) {
        data.put(key, value);
        return this;
    }

    public StepDefinition withResult(Result result) {
        this.result = result;
        return this;
    }

    public StepDefinition withResult(String message, Status status, long durationInNano) {
        this.result = Result.builder().durationInNano(durationInNano).errorMessage(message).status(status).build();
        return this;
    }

    public StepDefinition WithEmbeddigs(Embedded embedded) {
        if(Objects.isNull(embeddings)) {
            return this;
        }
        this.embeddings.add(embedded);
        return this;
    }

    public StepDefinition WithEmbeddigs(MimeType mimeType, String data, String name) {
        this.embeddings.add(Embedded.builder().mimeType(mimeType).data(data).name(name).build());
        return this;
    }

    public StepDefinition withEmbeddings(List<Embedded> embeddings) {
        if(Objects.isNull(embeddings) || embeddings.size() == 0) {
            return this;
        }
        this.embeddings.addAll(embeddings);
        return this;
    }
}
