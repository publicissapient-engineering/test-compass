package com.publicissapient.anoroc.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class BatchRequest {

    @NotNull
    private String name;

    private String description;

    private List<Long> businessScenarios;

    private Long applicationId;

}
