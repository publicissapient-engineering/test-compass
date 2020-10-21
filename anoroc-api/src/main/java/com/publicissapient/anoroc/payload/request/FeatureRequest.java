package com.publicissapient.anoroc.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
final public class FeatureRequest {

    private Long id;

    @NotBlank
    @Size(min =3,max = 512)
    private String name;

    @NotNull
    private String content;

    @NotNull
    @JsonProperty(value = "xpath")
    private String xPath;

    @NotNull
    @JsonProperty(value = "feature_type")
    private FeatureType featureType ;

    @JsonProperty("include_features")
    private List<Long> includeFeatures;

    @JsonProperty(value = "application_id")
    private long applicationId;

}
