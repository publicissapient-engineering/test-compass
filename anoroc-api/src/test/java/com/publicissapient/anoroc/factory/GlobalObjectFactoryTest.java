package com.publicissapient.anoroc.factory;


import com.publicissapient.anoroc.model.GlobalObject;
import com.publicissapient.anoroc.payload.request.GlobalObjectRequest;
import com.publicissapient.anoroc.payload.response.GlobalObjectCount;
import com.publicissapient.anoroc.payload.response.GlobalObjectResponse;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@ActiveProfiles("test")
public class GlobalObjectFactoryTest {

    public static GlobalObject getGlobalObject() {
        return GlobalObject.builder().content("{\"karate\":\"sample\"}").name("sample_object_repo").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).application(ApplicationFactoryTest.getApplication(1l)).build();
    }

    public static GlobalObject getGlobalObject(long id) {
        return GlobalObject.builder().id(id).content("{\"karate\":\"sample\"}").name("sample_object_repo").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).application(ApplicationFactoryTest.getApplication(1l)).build();
    }

    public static GlobalObject getGlobalObject(long id, String content) {
        return GlobalObject.builder().id(id).content(content).name("sample_object_repo").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).application(ApplicationFactoryTest.getApplication(1l)).build();
    }

    public static GlobalObjectRequest getGlobalObjectRequest() {
        return GlobalObjectRequest.builder().content("{\"karate\":\"sample\"}").name("sample_object_repo").applicationId(1l).build();
    }

    public static GlobalObjectRequest getGlobalObjectRequest(long id) {
        return GlobalObjectRequest.builder().id(id).content("{\"karate\":\"sample\"}").name("sample_object_repo").applicationId(1l).build();
    }

    public static GlobalObjectResponse getGlobalObjectResponse() {
        return GlobalObjectResponse.builder().id(1l).content("{\"karate\":\"sample\"}").name("sample_object_repo").build();
    }

    public static GlobalObjectCount getGlobalObjectCount() {
        return GlobalObjectCount.builder().count(1l).build();
    }
}
