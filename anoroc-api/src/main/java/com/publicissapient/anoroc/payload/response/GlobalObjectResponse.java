package com.publicissapient.anoroc.payload.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalObjectResponse implements Serializable {

    private String name;

    private String content;

    private long id;

    @JsonProperty(value = "created_date_time")
    private LocalDateTime createdDateTime;

    @JsonProperty(value = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @JsonProperty(value = "application")
    private ApplicationResponse application;
}

