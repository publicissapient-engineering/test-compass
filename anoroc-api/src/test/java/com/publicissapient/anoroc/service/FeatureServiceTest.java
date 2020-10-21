package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.FeatureNotFoundException;
import com.publicissapient.anoroc.factory.ApplicationFactoryTest;
import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.model.FeatureEntity;
import com.publicissapient.anoroc.payload.mapper.FeatureModelMapper;
import com.publicissapient.anoroc.repository.ApplicationRepository;
import com.publicissapient.anoroc.repository.FeatureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@ActiveProfiles("test")
public class FeatureServiceTest {
    @Autowired
    private FeatureService featureService;

    @MockBean
    private FeatureRepository featureRepository;

    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private FeatureModelMapper mapper;

    @Test
    void should_return_feature_count() {
        when(featureRepository.count()).thenReturn(4L);
        assertThat(featureService.totalCount()).isEqualTo(4L);
        verify(featureRepository).count();
    }

    @Test
    void should_return_feature_list() {
        when(featureRepository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(featureListCount()).isEqualTo(0);
        verify(featureRepository).findAll(any(Pageable.class));
    }

    @Test
    void should_throw_Feature_not_found_exception() {
        when(featureRepository.findById(anyLong())).thenThrow(FeatureNotFoundException.class);
        assertThrows(FeatureNotFoundException.class, () -> featureService.featureById(1L));
        verify(featureRepository).findById(anyLong());
    }

    @Test
    void should_return_feature_details_by_Id() {
        when(featureRepository.findById(anyLong())).thenReturn(Optional.of(FeatureFactory.feature()));
        assertThat(featureById()).isEqualTo(1L);
        verify(featureRepository).findById(anyLong());
    }

    @Test
    void should_save_feature_details() {
        when(featureRepository.save(any(FeatureEntity.class))).thenReturn(FeatureFactory.feature());
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(ApplicationFactoryTest.getApplication(1l)));
        assertThat(persistedFeatureId()).isEqualTo(1L);
        verify(featureRepository).save(any(FeatureEntity.class));
    }

    @Test
    void should_get_feature_count_by_application_id() {
        when(featureRepository.countByApplicationId(anyLong())).thenReturn(1l);
        assertThat(featureService.getFeaturescount(1l)).isGreaterThan(0);
    }

    @Test
    void should_be_able_to_get_features_for_an_business_scenario_id() {
        when(featureRepository.findByBusinessScenariosId(anyLong())).thenReturn(Arrays.asList(FeatureFactory.feature(1l)));
        assertThat(featureService.getFeaturesByBusinessScenarioId(1l)).isNotEmpty();
    }

    @Test
    void should_be_able_to_get_feature_list_contains_name() {
        when(featureRepository.findByIdNotAndNameContainsIgnoreCase(anyLong(), anyString(), any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(featureService.getFeaturesByNameContainsAndIdNotIn(0, 5, "name", 1l)).isEmpty();
    }

    @Test
    void should_be_able_to_get_include_feature_list_with_searchText() {
        when(featureRepository.findByIdNotAndNameContainsIgnoreCase(anyLong(), anyString(), any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(featureService.featureList(0, 5, "name", 1l)).isEmpty();
    }

    @Test
    void should_be_able_to_get_include_feature_list_with_empty_searchText() {
        when(featureRepository.findByIdNot(anyLong(), any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(featureService.featureList(0, 5,  "", 1l)).isEmpty();
    }

    private Long persistedFeatureId() {
        return featureService.save(FeatureFactory.feature(), 1l).getId();
    }

    private int featureListCount() {
        return featureService.featureList(0, 5, "", 0).size();
    }

    private Long featureById() {
        return featureService.featureById(1L).getId();
    }



}
