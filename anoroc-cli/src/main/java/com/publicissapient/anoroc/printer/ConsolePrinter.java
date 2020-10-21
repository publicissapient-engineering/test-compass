package com.publicissapient.anoroc.printer;

import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import static java.lang.System.out;

public class ConsolePrinter {

    public static void printlnGreen(String statement) {
        out.println(AnsiOutput.toString(
                AnsiColor.BRIGHT_GREEN, statement, AnsiColor.DEFAULT));
    }

    public static void printlnRed(String statement) {
        out.println(AnsiOutput.toString(
                AnsiColor.BRIGHT_RED, statement, AnsiColor.DEFAULT));
    }

    public static void printlnYellow(String statement) {
        out.println(AnsiOutput.toString(
                AnsiColor.BRIGHT_YELLOW, statement, AnsiColor.DEFAULT));
    }

    public static void printlnCyan(String statement) {
        out.println(AnsiOutput.toString(
                AnsiColor.BRIGHT_CYAN, statement, AnsiColor.DEFAULT));
    }

    public static void println(String statement) {
        out.println(statement);
    }

}
