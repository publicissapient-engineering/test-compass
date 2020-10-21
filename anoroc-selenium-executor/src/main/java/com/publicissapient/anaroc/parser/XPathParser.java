package com.publicissapient.anaroc.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicissapient.anaroc.exception.XPathFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class XPathParser {

    private XPathParser() {
    }

    public static Map<String, String> xpaths(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(fileName), LinkedHashMap.class);
        } catch (IOException e) {
            throw new XPathFileNotFoundException("File not found");
        }
    }

    public static Map<String, String> xpathsFromSoure(String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, LinkedHashMap.class);
        } catch (IOException e) {
            throw new XPathFileNotFoundException("File not found");
        }
    }
}
