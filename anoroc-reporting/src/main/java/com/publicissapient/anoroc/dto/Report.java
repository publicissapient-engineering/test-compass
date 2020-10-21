package com.publicissapient.anoroc.dto;

import com.publicissapient.anoroc.model.Feature;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.publicissapient.anoroc.generator.JsonReportGenerator.DATE_FORMAT;

@Data
public class Report {

    private String reportDirectory = "reports/" + new SimpleDateFormat(DATE_FORMAT).format(new Date());

    private String projectName = "Anoroc Functional Testing";

    private List<String> jsonFiles;

    private List<String> htmlFiles;

    private List<Feature> features;

    public Report(List<Feature> features) {
        this.features = features;
        this.jsonFiles = new ArrayList<>();
    }

}
