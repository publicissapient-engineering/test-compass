package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.payload.request.ApplicationRequest;
import com.publicissapient.anoroc.payload.response.ApplicationCount;
import com.publicissapient.anoroc.payload.response.ApplicationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationModelMapper {

    private ModelMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ModelMapper();
        mapper.typeMap(Application.class, ApplicationResponse.class)
                .addMapping(src -> src.getCreatedAt(), ApplicationResponse::setCreatedDateTime)
                .addMapping(src -> src.getUpdatedAt(), ApplicationResponse::setUpdatedDateTime);
    }

    public ApplicationResponse applicationResponsePayload(Application application) {
        return mapper.map(application, ApplicationResponse.class);
    }

    public List<ApplicationResponse> applicationResponsePayloads(List<Application> applications) {
        return applications.stream().map(this::applicationResponsePayload).collect(Collectors.toList());
    }

    public Application applicationEntity(ApplicationRequest requestPayload) {
        return mapper.map(requestPayload, Application.class);
    }

    public ApplicationCount applicationCount(Long count){
        return ApplicationCount.builder().count(count).build();
    }

}
