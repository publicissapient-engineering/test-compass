Feature: CLOSE TAB Command Feature

 Scenario Outline: CLOSE TAB command using Tab Title scenario
    Given I OPEN [url]
    And  VALIDATE_TITLE <TextToVerify>
    Then CLOSE_TAB <TabTitle>
   Examples:
   |TextToVerify |TabTitle   |
   |Google        |Google     |
   |Google        |Google1    |
   |Google        |           |

  Scenario Outline: CLOSE TAB command using Tab Title scenario
    Given I OPEN [heroku_url]
    Then CLOSE_TAB <TabTitle>
    Examples:
     |TabTitle   |
     |           |


