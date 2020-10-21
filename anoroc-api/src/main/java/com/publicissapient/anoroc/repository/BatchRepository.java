package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {

    Page<Batch> findAllByApplicationId(Long applicationId, Pageable pagable);

}
