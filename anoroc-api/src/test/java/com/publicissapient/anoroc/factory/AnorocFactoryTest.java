package com.publicissapient.anoroc.factory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

public class AnorocFactoryTest {

    public static Map<String, Object> getUriVariables() {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page",0);
        uriVariables.put("size",5);
        return uriVariables;
    }

    public static PageRequest pageRequestBuilder(Integer page, Integer size) {
        return PageRequest.of(page, size, Sort.by("createdAt").descending());
    }
}
