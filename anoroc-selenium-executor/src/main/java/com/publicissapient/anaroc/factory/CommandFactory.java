package com.publicissapient.anaroc.factory;

import com.publicissapient.anaroc.command.*;
import com.publicissapient.anaroc.util.CommandType;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private CommandFactory() {
    }

    public static CommandFactory build() {
        return new CommandFactory();
    }

    public Command getCommand(StepDefinition stepDefinition,
                              WebDriver webDriver,
                              Map<String, String> xPaths) {
        String instruction = stepDefinition.getInstruction();
        Map<String, String> xPathsInStepDefinition = extractXPath(stepDefinition, xPaths);

        return identifyCommand(stepDefinition,
                webDriver,
                instruction,
                xPathsInStepDefinition);
    }

    private Command identifyCommand(StepDefinition stepDefinition,
                                    WebDriver webDriver,
                                    String instruction,
                                    Map<String, String> xPathsInStepDefinition) {
        Command command = null;
        CommandType commandType = CommandType.getCommandType(instruction);
        switch (commandType) {
            case ENTER:
                command = new EnterCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SEE:
                command = new SeeCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case OPEN:
                command = new OpenCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case CLICK:
                command = new ClickCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SELECT:
                command = new SelectCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case VERIFY:
                command = new VerifyCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case NAVIGATE:
                command = new NavigateBack(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case HIGHLIGHT:
                command = new HighlightCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case CLEAR_TEXT:
                command = new ClearTextCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case DELETE_TEXT:
                command = new DeleteTextCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case PAGE_REFRESH:
                command = new PageRefreshComand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case HOVER:
                command = new HoverCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case UNSELECT:
                command = new UnSelect(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case VALIDATE_TITLE:
                command = new ValidateTitle(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case ENTER_APPEND:
                command = new EnterAppend(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case CLOSE_TAB:
                command = new CloseTab(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SCROLL_BOTTOM:
                command = new ScrollBottom(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SCROLL_DOWN:
                command = new ScrollDown(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SCROLL_FIND:
                command = new ScrollFind(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SCROLL_UP:
                command = new ScrollUp(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SCROLL_TOP:
                command = new ScrollTop(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case PAUSE_INSEC:
                command = new Sleep(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case NEWTAB:
                command = new NewTab(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SWITCH_WINDOW:
                command = new SwitchWindow(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case DELAY:
                command = new Delay(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case HEIGHT:
                command = new HeightCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case WIDTH:
                command = new WidthCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            case SELECT_NODE_FROM_TREE:
                command = new SelectNodeFromTreeCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
            default:
                command = new UnknownCommand(stepDefinition, webDriver, xPathsInStepDefinition);
                break;
        }
        return command;
    }


    private Map<String, String> extractXPath(StepDefinition stepDefinition, Map<String, String> xPaths) {
        String instruction = stepDefinition.getInstruction();
        Map<String, String> xPathsForStepDefinition = new HashMap<>();
        xPaths.keySet().forEach(xPath -> {
            if (containsSquareBrackets(instruction, xPath) || containsAngularBrackets(instruction, xPath)) {
                xPathsForStepDefinition.put(xPath, xPaths.get(xPath));
            }
        });
        return xPathsForStepDefinition;
    }

    private boolean containsAngularBrackets(String instruction, String xPath) {
        return instruction.contains("<" + xPath + ">");
    }

    private static boolean containsSquareBrackets(String instruction, String xPath) {
        return instruction.contains("[" + xPath + "]");
    }

}
