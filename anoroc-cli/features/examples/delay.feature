Feature: SWITCH WINDOW Command Feature

 Scenario Outline: SWITCH WINDOW command scenario
    Given I OPEN [url]
    And  DELAY <delayInSec>
    And  NEWTAB [newUrl]
    And  DELAY <delayInSec>
    Then SWITCH_WINDOW <switchTab>
   Examples:
   |switchTab|delayInSec|
   |Google  |2          |


