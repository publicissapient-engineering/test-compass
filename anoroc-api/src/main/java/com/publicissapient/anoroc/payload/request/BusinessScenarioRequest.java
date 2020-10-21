package com.publicissapient.anoroc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessScenarioRequest {

    private long id;

    private String name;

    private String description;

    private List<Long> featureIds;

}
