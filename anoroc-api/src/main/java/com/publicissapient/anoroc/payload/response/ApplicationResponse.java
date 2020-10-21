package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse implements Serializable {

    private long id;

    private String name;

    @JsonProperty(value = "created_date_time")
    private LocalDateTime createdDateTime;

    @JsonProperty(value = "updated_date_time")
    private LocalDateTime updatedDateTime;

    private long featureCount;
}

