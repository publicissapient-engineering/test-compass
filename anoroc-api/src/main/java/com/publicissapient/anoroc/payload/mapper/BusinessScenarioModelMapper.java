package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.payload.request.BusinessScenarioRequest;
import com.publicissapient.anoroc.payload.response.BusinessScenarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessScenarioModelMapper {

    private ModelMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ModelMapper();
        mapper.typeMap(BusinessScenario.class, BusinessScenarioResponse.class)
                .addMapping(src -> src.getCreatedAt(), BusinessScenarioResponse::setCreatedDateTime)
                .addMapping(src -> src.getUpdatedAt(), BusinessScenarioResponse::setUpdatedDateTime);

    }

    public BusinessScenarioResponse businessScenarioResponsePayload(BusinessScenario businessScenario){
        return mapper.map(businessScenario, BusinessScenarioResponse.class);
    }

    public List<BusinessScenarioResponse> businessScenarioResponsePayLoads(List<BusinessScenario> businessScenarios) {
        return businessScenarios.stream().map(this::businessScenarioResponsePayload).collect(Collectors.toList());
    }

    public BusinessScenario businessScenario(BusinessScenarioRequest businessScenarioRequest) {
        return mapper.map(businessScenarioRequest, BusinessScenario.class);
    }

}
