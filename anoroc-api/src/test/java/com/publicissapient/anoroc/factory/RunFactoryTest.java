package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.payload.request.FeatureRunRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;

@ActiveProfiles("test")
public class RunFactoryTest {

    public static Run run() {
        return Run.builder().createdAt(LocalDateTime.now()).status(RunStatus.RUNNING)
                .features(Arrays.asList(FeatureFactory.feature()))
                .updatedAt(LocalDateTime.now())
                .environment(EnvironmentFactoryTest.getEnvironment(1l))
                .build();
    }

    public static Run run(long id) {
        return Run.builder().id(id).createdAt(LocalDateTime.now()).status(RunStatus.RUNNING)
                .features(Arrays.asList(FeatureFactory.feature()))
                .environment(EnvironmentFactoryTest.getEnvironment(1l))
                .updatedAt(LocalDateTime.now()).build();
    }

    public static Run run(long id, long featureId) {
        return Run.builder().id(id).createdAt(LocalDateTime.now()).status(RunStatus.RUNNING)
                .features(Arrays.asList(FeatureFactory.feature(featureId)))
                .environment(EnvironmentFactoryTest.getEnvironment(1l))
                .updatedAt(LocalDateTime.now()).build();
    }

    public static FeatureRunRequest featureRunRequest() {
        return FeatureRunRequest.builder().environmentId(1l).featureId(1l).build();
    }
}
