package com.publicissapient.anoroc.payload.mapper;

import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.payload.request.FeatureRequest;
import com.publicissapient.anoroc.payload.response.FeatureResponse;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeatureModelMapper {

    private ModelMapper mapper = new ModelMapper();

    public FeatureModelMapper() {
        this.mapper.typeMap(FeatureEntity.class, FeatureResponse.class)
                .addMapping(src -> src.getCreatedAt(), FeatureResponse::setCreatedDateTime)
                .addMapping(src -> src.getApplication(), FeatureResponse::setApplication);

    }

    public FeatureResponse featurePayload(FeatureEntity featureEntity) {
        return mapper.map(featureEntity, FeatureResponse.class);
    }

    public List<FeatureResponse> featureListPayload(List<FeatureEntity> featureEntityList) {
        return featureEntityList.stream().map(feature -> mapper.map(feature, FeatureResponse.class)).collect(Collectors.toList());
    }

    public FeatureEntity featureEntity(FeatureRequest requestBody) {
       return mapper.map(requestBody, FeatureEntity.class);
    }

    public Long[] getIncludeFeatureIds(FeatureEntity entity) {
        int size = CollectionUtils.isEmpty(entity.getIncludeFeatures()) ? 0 : entity.getIncludeFeatures().size();
        return entity.getIncludeFeatures().toArray(new Long[size]);
    }

}
