package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.model.Environment;
import com.publicissapient.anoroc.payload.request.EnvironmentRequest;

import java.time.LocalDateTime;

public class EnvironmentFactoryTest {

    public static Environment getEnvironment() {
        return Environment.builder().name("Dev").karateContent("{\"karate\":{},\"cats\":{}}")
                .anorocContent("{\"karate\":{},\"cats\":{}}")
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }

    public static Environment getEnvironment(long id) {
        return Environment.builder().id(id).karateContent("{\"karate\":{},\"cats\":{}}")
                .anorocContent("{\"karate\":{},\"cats\":{}}")
                .name("Dev").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }

    public static EnvironmentRequest getEnvRequest() {
       return EnvironmentRequest.builder().karateContent("{\"karate\":{},\"cats\":{}}")
               .anorocContent("{\"karate\":{},\"cats\":{}}")
               .name("dev").build();
    }

    public static EnvironmentRequest getEnvRequest(long id) {
        return EnvironmentRequest.builder().id(id).karateContent("{\"karate\":{},\"catsupdate\":{}}")
                .anorocContent("{\"karate\":{},\"cats\":{}}")
                .name("dev_update").build();
    }
}
