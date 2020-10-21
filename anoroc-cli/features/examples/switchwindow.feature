Feature: SWITCH WINDOW Command Feature

 Scenario Outline: SWITCH WINDOW command scenario
    Given I OPEN [url]
    And  NEWTAB [newUrl]
    Then SWITCH_WINDOW <switchTab>
   Examples:
   |switchTab|
   |Google  |


