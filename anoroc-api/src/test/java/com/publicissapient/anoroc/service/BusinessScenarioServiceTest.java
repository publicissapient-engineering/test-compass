package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.exception.BusinessScenarioNotFoundException;
import com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest;
import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.model.BusinessScenario;
import com.publicissapient.anoroc.repository.BusinessScenarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.publicissapient.anoroc.factory.BusinessScenarioFactoryTest.getBusinessScenarioEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class BusinessScenarioServiceTest {

    @Autowired
    private BusinessScenarioService businessScenarioService;

    @MockBean
    private BusinessScenarioRepository businessScenarioRepository;

    @MockBean
    private FeatureService featureService;

    @Test
    void should_be_able_to_save_business_scenario_obj_with_feature_ids() {
        when(featureService.featureById(anyLong())).thenReturn(FeatureFactory.feature());
        when(businessScenarioRepository.save(any())).thenReturn(getBusinessScenarioEntity(1l, Arrays.asList(FeatureFactory.feature().getId())));
        BusinessScenario businessScenarioEntity = businessScenarioService.save(getBusinessScenarioEntity(1l, Arrays.asList(1l, 2l, 3l)), Arrays.asList(1l, 2l, 3l));
        assertThat(businessScenarioEntity).isNotNull();
    }

    @Test
    void should_be_able_to_save_business_scenario_obj_without_feature_ids() {
        when(businessScenarioRepository.save(any())).thenReturn(getBusinessScenarioEntity(1l, Collections.emptyList()));
        BusinessScenario businessScenarioEntity = businessScenarioService.save(getBusinessScenarioEntity(1l, Collections.emptyList()), Collections.emptyList());
        assertThat(businessScenarioEntity).isNotNull();
    }

    @Test
    void should_be_able_to_get_business_scenario_for_an_id() {
        when(featureService.getFeaturesByBusinessScenarioId(anyLong())).thenReturn(Arrays.asList(FeatureFactory.feature(1l)));
        when(businessScenarioRepository.findById(any())).thenReturn(Optional.of(BusinessScenarioFactoryTest.getBusinessScenarioEntity(1l, Arrays.asList(1l))));
        assertThat(businessScenarioService.getBusinessScenario(1l)).isNotNull();
    }

    @Test
    void should_throw_business_scenario_not_found_exception_for_an_invalid_id() {
        when(businessScenarioRepository.findById(any())).thenThrow(BusinessScenarioNotFoundException.class);
        Assertions.assertThrows(BusinessScenarioNotFoundException.class, ()-> businessScenarioService.getBusinessScenario(1l));
    }

    @Test
    void should_be_able_to_get_business_scenario_list() {
        when(featureService.getFeaturesByBusinessScenarioId(anyLong())).thenReturn(Arrays.asList(FeatureFactory.feature(1l)));
        when(businessScenarioRepository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(businessScenarioService.getBusinessScenarios(PageRequest.of(1, 5))).isEmpty();
    }

    @Test
    void should_be_able_to_get_business_scenario_count() {
        when(businessScenarioRepository.count()).thenReturn(1l);
        assertThat(businessScenarioService.getCount()).isEqualTo(1l);
    }

    @Test
    void should_be_able_to_get_features_for_an_business_scenario_id() {
        when(featureService.getFeaturesByBusinessScenarioId(anyLong())).thenReturn(Arrays.asList(FeatureFactory.feature(1l)));
        assertThat(businessScenarioService.getFeatures(1l)).isNotEmpty();
    }

    @Test
    void should_be_able_to_remove_features_for_an_business_scenario_id() {
        when(featureService.getFeaturesByBusinessScenarioId(anyLong())).thenReturn(Arrays.asList(FeatureFactory.feature(1l),FeatureFactory.feature(2l)));
        when(businessScenarioRepository.findById(any())).thenReturn(Optional.of(BusinessScenarioFactoryTest.getBusinessScenarioEntity(1l, Arrays.asList(1l, 2l))));
        when(businessScenarioRepository.save(any())).thenReturn(getBusinessScenarioEntity(1l, Arrays.asList(1l)));
        BusinessScenario businessScenario = businessScenarioService.removeFeatureFromBusinessScenario(1l, Arrays.asList(2l));
        assertThat(businessScenario).isNotNull();
        assertThat(businessScenario.getFeatures().get(0).getId()).isEqualTo(1l);
    }
}
