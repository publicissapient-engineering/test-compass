package com.publicissapient.anaroc.factory;

import com.publicissapient.anaroc.command.*;
import com.publicissapient.anoroc.model.StepDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class CommandFactoryTest {

    private static WebDriver webDriver;

    @BeforeAll
    public static void init() throws Exception {
        webDriver =  WebDriverFactoryTest.localFireFoxWebDriver();
    }

    @Test
    void should_return_open_command() {
        StepDefinition openStepDefinition = StepDefinition.build().withInstruction("I OPEN [url]");
        assertThat(CommandFactory.build().getCommand(openStepDefinition, null, new HashMap<>())).isInstanceOf(OpenCommand.class);
    }

    @Test
    void should_return_enter_command() {
        StepDefinition enterStepDefinition = StepDefinition.build().withInstruction("I ENTER <username>");
        assertThat(CommandFactory.build().getCommand(enterStepDefinition, null, new HashMap<>())).isInstanceOf(EnterCommand.class);
    }

    @Test
    void should_return_click_command() {
        StepDefinition enterStepDefinition = StepDefinition.build().withInstruction("I CLICK [some_button]");
        assertThat(CommandFactory.build().getCommand(enterStepDefinition, null, new HashMap<>())).isInstanceOf(ClickCommand.class);
    }

    @Test
    void should_return_see_command() {
        StepDefinition enterStepDefinition = StepDefinition.build().withInstruction("I SEE [some_text]");
        assertThat(CommandFactory.build().getCommand(enterStepDefinition, null, new HashMap<>())).isInstanceOf(SeeCommand.class);
    }

    @Test
    void should_return_unknown_command() {
        StepDefinition enterStepDefinition = StepDefinition.build().withInstruction("I give UNKNOWN_INSTRUCTION to execute");
        assertThat(CommandFactory.build().getCommand(enterStepDefinition, null, new HashMap<>())).isInstanceOf(UnknownCommand.class);
    }

    @Test
    void should_return_clear_text_command() {
        StepDefinition clearTextStepDefinition = StepDefinition.build().withInstruction("I CLEAR_TEXT [some_field]");
        assertThat(CommandFactory.build().getCommand(clearTextStepDefinition, null, new HashMap<>())).isInstanceOf(ClearTextCommand.class);
    }

    @Test
    void should_return_verify_text_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("I VERIFY <some_field>");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, null, new HashMap<>())).isInstanceOf(VerifyCommand.class);
    }

    @Test
    void should_return_enter_append_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("I ENTER_APPEND [text]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(EnterAppend.class);
    }

    @Test
    void should_return_hover_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("I HOVER mouser over at [text]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(HoverCommand.class);
    }

    @Test
    void should_return_new_tab_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Give [url] in NEWTAB");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(NewTab.class);
    }

    @Test
    void should_return_switch_window_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SWITCH_WINDOW tab");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(SwitchWindow.class);
    }

    @Test
    void should_return_unselect_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then UNSELECT [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(UnSelect.class);
    }

    @Test
    void should_return_validate_title_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then VALIDATE_TITLE [tabTitle]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(ValidateTitle.class);
    }

    @Test
    void should_return_delay_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then DELAY [SEC]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(Delay.class);
    }

    @Test
    void should_return_sleep_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then PAUSE_INSEC [SEC]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(Sleep.class);
    }

    @Test
    void should_return_page_refresh_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then PAGE_REFRESH [URL]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(PageRefreshComand.class);
    }

    @Test
    void should_return_navigate_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then NAVIGATE [URL]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(NavigateBack.class);
    }

    @Test
    void should_return_highlight_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then HIGHLIGHT [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(HighlightCommand.class);
    }

    @Test
    void should_return_close_tab_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then CLOSE_TAB [tabTitle]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(CloseTab.class);
    }

    @Test
    void should_return_delete_text_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then DELETE_TEXT [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(DeleteTextCommand.class);
    }

    @Test
    void should_return_select_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SELECT [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(SelectCommand.class);
    }

    @Test
    void should_return_scroll_up_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SCROLL_UP [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(ScrollUp.class);
    }

    @Test
    void should_return_scroll_down_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SCROLL_DOWN [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(ScrollDown.class);
    }

    @Test
    void should_return_scroll_top_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SCROLL_TOP [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(ScrollTop.class);
    }

    @Test
    void should_return_scroll_bottom_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SCROLL_BOTTOM");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(ScrollBottom.class);
    }

    @Test
    void should_return_scroll_find_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SCROLL_FIND [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(ScrollFind.class);
    }

    @Test
    void should_return_height_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then HEIGHT [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(HeightCommand.class);
    }

    @Test
    void should_return_width_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then WIDTH [element]");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(WidthCommand.class);
    }

    @Test
    void should_return_select_node_from_tree_command() {
        StepDefinition verifyStepDefinition = StepDefinition.build().withInstruction("Then SELECT_NODE_FROM_TREE <xpath>");
        assertThat(CommandFactory.build().getCommand(verifyStepDefinition, webDriver, new HashMap<>())).isInstanceOf(SelectNodeFromTreeCommand.class);
    }

}
