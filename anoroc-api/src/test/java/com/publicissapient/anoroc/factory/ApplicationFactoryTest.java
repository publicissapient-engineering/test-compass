package com.publicissapient.anoroc.factory;


import com.publicissapient.anoroc.model.Application;
import com.publicissapient.anoroc.payload.request.ApplicationRequest;

import java.time.LocalDateTime;

public class ApplicationFactoryTest extends  AnorocFactoryTest {

    public static Application getApplication(long id) {
       return Application.builder().id(id).name("Anoroc2")
               .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
               .build();
    }

    public static Application getApplication() {
        return Application.builder().name("Anoroc2").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public static ApplicationRequest getRequestPayload() {
        return ApplicationRequest.builder().name("Anoroc").build();
    }
}
