package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureResponse implements Serializable {

    private static final long serialVersionUID = 4L;

    private long id;

    private String name;

    @JsonProperty(value = "created_date_time")
    private LocalDateTime createdDateTime;

    private String content;

    private String xpath;

    private FeatureType featureType;

    @JsonProperty(value = "application")
    private ApplicationResponse application;

    @JsonProperty("include_features")
    private List<Long> includeFeatures;

}
