package com.publicissapient.anoroc.model;

public enum MimeType {

    PNG_IMAGE("image/png"),
    GIF_IMAGE("image/gif"),
    BMP_IMAGE("image/bmp"),
    JPEG_IMAGE("image/jpeg");

    private String mimeType;


    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getType() {
        return this.mimeType;
    }
}
