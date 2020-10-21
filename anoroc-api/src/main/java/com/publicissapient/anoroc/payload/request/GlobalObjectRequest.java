package com.publicissapient.anoroc.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalObjectRequest implements Serializable {

    private String name;

    private String content;

    private long id;

    @JsonProperty(value = "application_id")
    private long applicationId;

}
