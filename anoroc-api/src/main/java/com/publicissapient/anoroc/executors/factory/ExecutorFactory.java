package com.publicissapient.anoroc.executors.factory;

import com.publicissapient.anoroc.executors.Executor;
import com.publicissapient.anoroc.executors.anoroc.AnorocExecutor;
import com.publicissapient.anoroc.executors.karate.KarateExecutor;
import com.publicissapient.anoroc.model.FeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecutorFactory {

    @Autowired
    private AnorocExecutor anorocExecutor;

    @Autowired
    private KarateExecutor karateExecutor;

    public Executor getExecutor(FeatureType featureType) {
        switch (featureType) {
            case ANOROC:
                return anorocExecutor;
            case KARATE:
            default:
                return karateExecutor;

        }
    }
}
