package com.publicissapient.anoroc.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Embedded {
    private MimeType mimeType;
    private String data;
    private String name;
}
