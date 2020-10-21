package com.publicissapient.anoroc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncludePaginationRequest {

    @NotNull
    @Min(0)
    private Integer page = 0;

    @NotNull
    @Min(1)
    private Integer size = 10;

    @NotNull
    private long notIn;

    private String name;

}
