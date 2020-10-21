package com.publicissapient.anoroc.prompt;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class AnorocPrompt implements PromptProvider {

    public static final String ANOROC = "anoroc:>";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(ANOROC, AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
