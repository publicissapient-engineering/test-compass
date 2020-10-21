package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.GlobalObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalObjectRepository extends JpaRepository<GlobalObject, Long> {

    List<GlobalObject> findByApplicationId(long applicationId);

}
