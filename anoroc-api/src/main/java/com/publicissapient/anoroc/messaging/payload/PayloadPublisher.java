package com.publicissapient.anoroc.messaging.payload;


import com.publicissapient.anoroc.model.RunType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadPublisher {

    private long payloadId;

    private long environmentId;

    private long globalObjectId;

    private long businessScenarioId;

    private RunType runType;

}
