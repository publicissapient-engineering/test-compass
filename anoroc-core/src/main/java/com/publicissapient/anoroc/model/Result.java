package com.publicissapient.anoroc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Status status;

    private long durationInNano;

    private String errorMessage;

    private List<ScreenShot> screenShots = new ArrayList<>();

}
