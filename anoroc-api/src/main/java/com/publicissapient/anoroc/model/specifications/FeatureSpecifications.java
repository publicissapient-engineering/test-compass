package com.publicissapient.anoroc.model.specifications;

import com.publicissapient.anoroc.model.FeatureEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FeatureSpecifications implements Specification<FeatureEntity> {
    @Override
    public Predicate toPredicate(Root<FeatureEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
