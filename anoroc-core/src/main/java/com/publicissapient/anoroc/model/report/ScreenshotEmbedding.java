package com.publicissapient.anoroc.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.ScreenShot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenshotEmbedding {

    @JsonProperty("mime_type")
    private String mimeType ;

    @JsonProperty("data")
    private String data;

    @JsonProperty("name")
    private String name;

    public ScreenshotEmbedding(String data){
        this.data = data;
    }

    public static ScreenshotEmbedding build(String data){
        return new ScreenshotEmbedding(data);
    }

    public ScreenshotEmbedding withData(ScreenShot screenShot){
        if(screenShot.getMimeType() == null)
            this.mimeType = "image/png";
        else
            this.mimeType = screenShot.getMimeType();
        this.name = screenShot.getName();
        return this;
    }

}
