package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.GlobalObject;
import com.publicissapient.anoroc.payload.request.GlobalObjectRequest;
import com.publicissapient.anoroc.payload.response.GlobalObjectCount;
import com.publicissapient.anoroc.payload.response.GlobalObjectResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GlobalObjectMapper {

    private ModelMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ModelMapper();
        mapper.typeMap(GlobalObject.class, GlobalObjectResponse.class)
                .addMapping(src -> src.getCreatedAt(), GlobalObjectResponse::setCreatedDateTime)
                .addMapping(src -> src.getUpdatedAt(), GlobalObjectResponse::setUpdatedDateTime);
    }

    public GlobalObjectResponse globalObjectResponsePayload(GlobalObject globalObject) {
        return mapper.map(globalObject, GlobalObjectResponse.class);
    }

    public List<GlobalObjectResponse> globalObjectResponsePayloads(List<GlobalObject> globalObjects) {
        return globalObjects.stream().map(this::globalObjectResponsePayload).collect(Collectors.toList());
    }

    public GlobalObject globalObjectEntity(GlobalObjectRequest requestPayload) {
        return mapper.map(requestPayload, GlobalObject.class);
    }

    public GlobalObjectCount globalObjectCount(Long count){
        return GlobalObjectCount.builder().count(count).build();
    }

}
