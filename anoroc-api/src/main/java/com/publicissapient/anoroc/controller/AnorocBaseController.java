package com.publicissapient.anoroc.controller;

import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.model.RunType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AnorocBaseController {

    protected PageRequest pageRequestBuilder(Integer page, Integer size) {
        return PageRequest.of(page, size, Sort.by("createdAt").descending());
    }

    public HttpStatus getStatusCode(long id) {
        return (id == 0l) ? HttpStatus.CREATED : HttpStatus.ACCEPTED;
    }

    public PayloadPublisher getPayloadRequest(long id, long environmentId, long globalObjectId, RunType runType) {
        return PayloadPublisher.builder().payloadId(id).environmentId(environmentId).globalObjectId(globalObjectId).runType(runType).build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected void internalServerError() {
    }
}
