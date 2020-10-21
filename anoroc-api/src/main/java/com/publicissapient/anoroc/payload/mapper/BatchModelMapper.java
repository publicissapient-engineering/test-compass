package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.Batch;
import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.payload.request.BatchRequest;
import com.publicissapient.anoroc.payload.response.BatchResponse;
import com.publicissapient.anoroc.payload.response.BusinessScenarioResponse;
import com.publicissapient.anoroc.repository.BusinessScenarioRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BatchModelMapper {

    @Autowired
    BusinessScenarioRepository businessScenarioRepository;

    private ModelMapper modelMapper = new ModelMapper();


    public Batch entity(BatchRequest request){
       Batch batch = modelMapper.map(request,Batch.class);
        return batch;
    }

    public BatchResponse response(Batch batch){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(batch,BatchResponse.class);
    }

    public List<BatchResponse> responseList(List<Batch> list) {
        return list.stream().map(batch ->response(batch)).collect(Collectors.toList());
    }


    Converter<List<BusinessScenario>, List<BusinessScenarioResponse>> listConverter = new AbstractConverter<List<BusinessScenario>, List<BusinessScenarioResponse>>() {
        protected List<BusinessScenarioResponse> convert(List<BusinessScenario> source) {
            return source == null ? null : source.stream().map(businessScenario -> modelMapper.map(businessScenario,BusinessScenarioResponse.class)).collect(Collectors.toList());
        }
    };



}
