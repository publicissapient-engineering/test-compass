package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.FeatureEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static com.publicissapient.anoroc.factory.FeatureFactory.feature;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:sql/application/insert_application.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:sql/features/insert_features.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = "classpath:sql/features/delete_features.sql")
})
@ActiveProfiles("test")
public class FeatureRepositoryTest {

    @Autowired
    private FeatureRepository featureRepository;

    @Test
    @Order(1)
    void should_return_feature_by_id() {
        assertThat(featureRepository.findById(1L).isPresent()).isTrue();
    }

    @Test
    void should_return_feature_count() {
        assertThat(featureRepository.count()).isEqualTo(2L);
    }

    @Test
    void should_return_pageable_feature_list() {
        assertThat(featureListCount()).isEqualTo(2);
    }


    @Test
    void should_be_able_to_save_feature_obj() {
        FeatureEntity entity  = featureRepository.save(feature());
        assertThat(featureRepository.getOne(entity.getId())).isNotNull();
    }

    @Test
    void should_get_features_by_application_id() {
        List<FeatureEntity> featureEntityList = featureRepository.findByApplicationId(1l);
        assertThat(featureEntityList).isNotEmpty();
    }

    @Test
    void should_get_feature_count_for_an_application_id() {
        long count = featureRepository.countByApplicationId(1l);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void should_be_get_list_of_features_by_name_and_id_not_in() {
        List<FeatureEntity> featureEntities = featureRepository.findByIdNotAndNameContainsIgnoreCase(1, "Bing", PageRequest.of(0, 5)).getContent();
        assertThat(featureEntities).hasSize(1);
    }

    @Test
    void should_be_get_list_of_features_by_name_and_without_id_not_in() {
        List<FeatureEntity> featureEntities = featureRepository.findByIdNotAndNameContainsIgnoreCase(0, "Bing", PageRequest.of(0, 5)).getContent();
        assertThat(featureEntities).hasSize(2);
    }

    @Test
    void should_be_get_list_of_features_by_id_not_in() {
        List<FeatureEntity> featureEntities = featureRepository.findByIdNot(1,  PageRequest.of(0, 5)).getContent();
        assertThat(featureEntities).hasSize(1);
    }

    @Test
    void should_get_count_of_features_contains_name() {
        long count = featureRepository.countByNameAndIdNotIn("Bing", 0l);
        assertThat(count).isEqualTo(2);
    }

    private int featureListCount() {
        return pageableServiceRequest().getContent().size();
    }

    private Page<FeatureEntity> pageableServiceRequest() {
        return featureRepository.findAll(PageRequest.of(0, 5));
    }
}
