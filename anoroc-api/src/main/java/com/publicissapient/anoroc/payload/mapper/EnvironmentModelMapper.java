package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.Environment;
import com.publicissapient.anoroc.payload.request.EnvironmentRequest;
import com.publicissapient.anoroc.payload.response.EnvironmentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnvironmentModelMapper {

    private ModelMapper modelMapper;

    @PostConstruct
    public void initMapper() {
        modelMapper = new ModelMapper();
        modelMapper.typeMap(Environment.class, EnvironmentResponse.class)
                .addMapping(src -> src.getCreatedAt(), EnvironmentResponse::setCreatedDateTime)
                .addMapping(src -> src.getUpdatedAt(), EnvironmentResponse::setUpdatedDateTime);
    }

    public EnvironmentResponse getResponsePayload(Environment environment) {
        return modelMapper.map(environment, EnvironmentResponse.class);
    }

    public List<EnvironmentResponse> getResponsePayloads(List<Environment> environment) {
        return environment.stream().map(this::getResponsePayload).collect(Collectors.toList());
    }

    public Environment getEnvEntity(EnvironmentRequest environmentRequest) {
        return modelMapper.map(environmentRequest, Environment.class);
    }
}
