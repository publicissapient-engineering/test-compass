package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.FeatureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureEntity,Long> {

    List<FeatureEntity> findByApplicationId(long applicationId);

    long countByApplicationId(long applicationId);

    List<FeatureEntity> findByBusinessScenariosId(long businessScenarioId);

    FeatureEntity findByName(String name);

    Page<FeatureEntity> findByIdNotAndNameContainsIgnoreCase(long id, String name, Pageable pageable);

    Page<FeatureEntity> findByIdNot(long id, Pageable pageable);

    @Query(value = "SELECT COUNT(f) FROM FeatureEntity f where UPPER(f.name) LIKE CONCAT('%',UPPER(:name),'%') and f.id not in :id")
    Long countByNameAndIdNotIn(String name, long id);

}
