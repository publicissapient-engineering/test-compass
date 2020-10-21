package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.payload.response.RunDetail;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RunModelMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public RunModelMapper() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Converter<RunStatus, Boolean> runStatusBooleanConverter = ctx -> ctx.getSource().isSuccess();

        TypeMap<Run, RunDetail> typeMap = modelMapper
                .typeMap(Run.class, RunDetail.class);
        typeMap.addMappings(mapper -> mapper
                .map(Run::getCreatedAt, RunDetail::setRunAt));
        modelMapper.addConverter(this::RunStatusConverter);
    }

    public List<RunDetail> runs(List<Run> runs) {
        return runs.stream().map(run -> modelMapper.map(run, RunDetail.class)).collect(Collectors.toList());
    }

    public RunDetail runDetails(Run run) {
        return modelMapper.map(run, RunDetail.class);
    }

    public String RunStatusConverter(MappingContext<RunStatus, String> context) {
        return context.getSource().getRawName();
    }
}
