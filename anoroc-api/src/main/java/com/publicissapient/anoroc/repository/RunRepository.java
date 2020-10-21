package com.publicissapient.anoroc.repository;

import com.publicissapient.anoroc.model.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {

}
