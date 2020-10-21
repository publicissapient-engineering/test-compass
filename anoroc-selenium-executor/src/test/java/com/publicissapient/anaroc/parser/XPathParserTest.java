package com.publicissapient.anaroc.parser;

import com.publicissapient.anaroc.exception.XPathFileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class XPathParserTest {

    @Test
    void should_throw_xpath_exception_for_invalid_file() {
        Assertions.assertThrows(XPathFileNotFoundException.class,
                () -> XPathParser.xpaths("/file-that-is-not-found.json"));
    }

    @Test
    void should_parse_a_valid_json_file() {
        Map<String, String> xPaths = XPathParser.xpaths("src/test/resources/xpaths/get-nada-account-creation-deletion.json");
        assertThat(xPaths).isEqualTo(expectedXpath());
    }

    @Test
    void should_parse_a_valid_json_content() {
        Map<String, String> xPaths = XPathParser.xpathsFromSoure("{\n" +
                "  \"url\": \"http://getnada.com\",\n" +
                "  \"add_inbox\": \"//*[@id=\\\"app\\\"]/add_inbox\",\n" +
                "  \"inbox_name\": \"//*[@id=\\\"app\\\"]/inbox_name\",\n" +
                "  \"accept\": \"//*[@id=\\\"app\\\"]/accept\",\n" +
                "  \"my_temp_inbox\": \"//*[@id=\\\"app\\\"]/my_temp_inbox\",\n" +
                "  \"my_temp_email\": \"//*[@id=\\\"app\\\"]/my_temp_email\"\n" +
                "}\n");
        assertThat(xPaths).isEqualTo(expectedXpath());
    }{

    }


    private Map<String, String> expectedXpath() {
        Map<String, String> xPaths = new LinkedHashMap<>();
        xPaths.put("url", "http://getnada.com");
        xPaths.put("add_inbox", "//*[@id=\"app\"]/add_inbox");
        xPaths.put("inbox_name", "//*[@id=\"app\"]/inbox_name");
        xPaths.put("accept", "//*[@id=\"app\"]/accept");
        xPaths.put("my_temp_inbox", "//*[@id=\"app\"]/my_temp_inbox");
        xPaths.put("my_temp_email", "//*[@id=\"app\"]/my_temp_email");
        return xPaths;
    }
}
