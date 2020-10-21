package com.publicissapient.anoroc.model;

public enum RunType {

    FEATURE,
    BUSINESS_SCENARIO,
    BATCH;

    public String getRawName() {
        return this.name().toLowerCase();
    }

    public String getLabel() {
        if (this.name().contains("_")) {
            String[] values = this.name().split("_");
            StringBuilder labelBuilder = new StringBuilder();
            for(String value : values) {
                labelBuilder.append(value.substring(0, 1).toUpperCase() + value.substring(1).toUpperCase());
            }
            return labelBuilder.toString();
        } else
            return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toUpperCase();
    }
}
