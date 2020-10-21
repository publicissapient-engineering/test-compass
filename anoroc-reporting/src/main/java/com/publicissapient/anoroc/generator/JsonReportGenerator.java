package com.publicissapient.anoroc.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.dto.Report;
import com.publicissapient.anoroc.model.report.FeatureReport;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JsonReportGenerator extends ReportGenerator {

    public static final String JSON_FILE_EXTENSION = ".json";
    public static final String DATE_FORMAT = "EEE, d MMM yyyy HH.mm.ss";

    public JsonReportGenerator(Report report) {
        super(report);
    }

    @Override
    public Report generateReport() {
        generateJsonReportFiles();
        return report;
    }

    private void generateJsonReportFiles() {
        ObjectMapper mapper = new ObjectMapper();
        report.getFeatures().stream().forEach(feature -> {
            report.getJsonFiles().add(writeJsonReportFile(feature, mapper));
        });
    }

    private String writeJsonReportFile(Feature feature, ObjectMapper mapper) {
        String jsonReportFileName = report.getReportDirectory() + "/" + feature.getName().replace(" ", "_") + JSON_FILE_EXTENSION;
        try {
            File jsonFile = checkFileExist(jsonReportFileName);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(jsonFile, Arrays.asList(FeatureReport.build(feature.getName()).buildScenarioOutlines(feature)));
            jsonReportFileName = jsonFile.getAbsolutePath();
        } catch (Exception e) {
            //TO DO
            e.printStackTrace();
        }
        return jsonReportFileName;
    }

    private File checkFileExist(String jsonReportFileName) throws IOException {
        File jsonFile = new File(jsonReportFileName);
        if (!jsonFile.exists()) {
            jsonFile.getParentFile().mkdirs();
            jsonFile.createNewFile();
        }
        return jsonFile;
    }
}
