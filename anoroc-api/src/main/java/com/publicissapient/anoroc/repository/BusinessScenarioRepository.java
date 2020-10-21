package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.BusinessScenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessScenarioRepository extends JpaRepository<BusinessScenario, Long> {


}
