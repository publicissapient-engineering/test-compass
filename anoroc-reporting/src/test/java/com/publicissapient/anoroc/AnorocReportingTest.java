package com.publicissapient.anoroc;

import com.publicissapient.anoroc.model.Feature;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AnorocReportingTest {

    public static final String HTML_REPORT_FILE_PATH = "reports/cucumber-html-reports/overview-features.html";
    public static final String SIMPLE_TEST_FEATURE_FILE = "src/test/resources/simple-test.feature";
    public static final String INVALID_REPORT_FILE_TYPE = "Invalid Report Type";


    @Test
    public void should_return_invalid_report_file_format() {
      //  assertThat(((String)new AnorocReporting().generateReport(new ArrayList<Feature>(),"abc"))).isEqualTo(INVALID_REPORT_FILE_TYPE);
    }

    @Test
    public void should_return_one_feature_json_Object() {
//        List<Feature> features = new FeatureParser().features(SIMPLE_TEST_FEATURE_FILE);
//        assertThat(new AnorocReporting().generateReport(features,"html")).isEqualTo(new File(HTML_REPORT_FILE_PATH).getAbsolutePath());
    }

    @Test
    void should_return_json_report_files() {
//        List<Feature> features = new FeatureParser().features(SIMPLE_TEST_FEATURE_FILE);
//        assertThat(((List<String>) new AnorocReporting().generateReport(features,"json"))).isEqualTo(expectedJsonFileList(features));
    }

    private List<String> expectedJsonFileList(List<Feature> features){

        return Arrays.asList(new File("reports").getAbsolutePath()+"/"+features.get(0).getName().replace(" ","_")+".json");
    }
}
