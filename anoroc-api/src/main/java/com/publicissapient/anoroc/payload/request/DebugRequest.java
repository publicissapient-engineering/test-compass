package com.publicissapient.anoroc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebugRequest {

    @NotNull
    private String instruction;

    private String data;

    private String xpath;

}
