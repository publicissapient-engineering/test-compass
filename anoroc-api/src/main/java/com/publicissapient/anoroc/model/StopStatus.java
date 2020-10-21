package com.publicissapient.anoroc.model;

public enum StopStatus {
    STOPPED,
    FAILED;

    public String getRawName(){
        return this.name().toLowerCase();
    }

    public String getLabel(){
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toUpperCase();
    }

    public boolean isStopped(){
        return this == STOPPED;
    }
}
