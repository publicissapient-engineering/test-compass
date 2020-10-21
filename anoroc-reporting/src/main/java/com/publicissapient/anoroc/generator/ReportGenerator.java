package com.publicissapient.anoroc.generator;

import com.publicissapient.anoroc.dto.Report;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public abstract class ReportGenerator {

    protected Report report;

    public abstract Report generateReport();
}
