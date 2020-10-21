package com.publicissapient.anoroc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
final public class PaginationRequest {
    @NotNull
    @Min(0)
    private Integer page = 0;

    @NotNull
    @Min(1)
    private Integer size = 10;

    @Min(1)
    private Long applicationId;

    private long notIn;

    private String name;


}
