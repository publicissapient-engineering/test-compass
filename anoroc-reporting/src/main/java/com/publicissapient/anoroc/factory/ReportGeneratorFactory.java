package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.dto.Report;
import com.publicissapient.anoroc.generator.HtmlReportGenerator;
import com.publicissapient.anoroc.generator.JsonReportGenerator;
import com.publicissapient.anoroc.generator.ReportGenerator;

public class ReportGeneratorFactory {

    public static ReportGenerator getHtmlReportGenerator(Report report){
            return new HtmlReportGenerator(report);
     }

    public static ReportGenerator getJsonReportGenerator(Report report){
        return new JsonReportGenerator(report);
    }

}
