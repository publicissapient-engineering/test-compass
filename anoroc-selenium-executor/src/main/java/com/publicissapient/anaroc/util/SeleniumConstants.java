package com.publicissapient.anaroc.util;

import org.openqa.selenium.Keys;

public enum SeleniumConstants {

    COMMA_SEPARATOR(","),
    WHITE_SPACE(" "),
    SCROLL_PAGE_BOTTOM_JS("window.scrollTo(0, document.body.scrollHeight);"),
    SCROLL_PAGE_TOP_JS("window.scrollTo(0, 0);"),
    SCROLL_PAGE_UP("scroll(0, %s);"),
    SCROLL_PAGE_DOWN("scroll(0, %s);"),
    SCROLL_TILL_FIND_ELEMENT("arguments[0].scrollIntoView();"),
    WINDOW_OPEN_JS("window.open('%s','_blank');"),
    CLOSE_TAB(Keys.CONTROL+"W"),
    SELECT_ALL(Keys.CONTROL+"a"),
    HTML_TAG_BODY("body"),
    TITLE_NOT_MATCH_FAILED_MSG("Actual '%s' title not matched with expected '%s' title"),
    ENTER_APPEND_FAILED_MSG("Unable to append the Text '%s'"),
    SWITCH_WINDOW_ERROR_MSG("Window with the title '%s' was not found"),
    UNSELECT_FAILED_MSG("Element was not selected, so I can't perform UNSELECT operation"),
    UNSELECT_FAILED_MSG_2("Can't perform UNSELECT operation for given an Element"),
    VALIDATE_TITLE_FAILED_MSG("Title '%s'  not matched!!"),
    ELEMENT_VALUE_ATTRIBUTE("value"),
    NOT_A_NUMBER_ERR_MSG("%s value should be numeric"),
    HEIGHT_NOT_EQUALS("Expected height %s not equals with the actual height %s"),
    WIDTH_NOT_EQUALS("Expected width %s not equals with the actual width %s"),
    MAT_TREE_NODE_TAG_NAME("mat-tree-node"),
    MAT_TREE_NODE_SPAN_ELE_XPATH("//span[contains(text(),'%s')]"),
    PARENT_NODE_JS("return arguments[0].parentNode;"),
    DELAY_NOT_INT_ERR_MSG("Delay param '%s' is Not a Number");

    private String value;

    private SeleniumConstants(String value){
        this.value  = value;
    }

    public String value(){
        return value;
    }


}
