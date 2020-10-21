package com.publicissapient.anaroc.util;

import com.publicissapient.anaroc.command.Command;
import com.publicissapient.anaroc.command.Scroll;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum CommandType {

    CLEAR_TEXT,
    CLICK,
    CLOSE_TAB,
    DELETE_TEXT,
    DELAY,
    ENTER,
    ENTER_APPEND,
    HIGHLIGHT,
    HOVER,
    NAVIGATE,
    NEWTAB,
    OPEN,
    PAGE_REFRESH,
    PAUSE_INSEC,
    SEE,
    SELECT,
    SCROLL_FIND,
    SCROLL_UP,
    SCROLL_BOTTOM,
    SCROLL_DOWN,
    SCROLL_TOP,
    SWITCH_WINDOW,
    SELECT_NODE_FROM_TREE,
    VERIFY,
    VALIDATE_TITLE,
    UNSELECT,
    HEIGHT,
    WIDTH,
    UNKNOWN;

    private CommandType(){
    }

    public String getAction() {
        return this.name().toUpperCase();
    }

    public static CommandType getCommandType(String instruction) {
        String[] instructions = instruction.split(SeleniumConstants.WHITE_SPACE.value());
        return Stream.of(CommandType.values())
                .filter(c -> CommandType.isInstructionsHasCommand(instructions, c))
                .findAny().orElse(CommandType.UNKNOWN);
    }

    private static  boolean isInstructionsHasCommand(String[] instructions, CommandType commandType) {
        return Arrays.stream(instructions).anyMatch(instruction -> StringUtils.equals(instruction, commandType.getAction()));
    }

}
