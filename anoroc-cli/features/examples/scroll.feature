Feature: SCROLL CommandS Feature
  Scenario Outline: SCROLL DOWN Scenario
    Given I OPEN [url]
    Then PAUSE_INSEC <sleepInSec>
    Then SCROLL_DOWN [pixel]
    Then PAUSE_INSEC <sleepInSec>
    Then SCROLL_BOTTOM
    Then PAUSE_INSEC <sleepInSec>
    Then SCROLL_UP [pixel]
    Then PAUSE_INSEC <sleepInSec>
    Then SCROLL_TOP
    Then PAUSE_INSEC <sleepInSec>
    Then SCROLL_FIND [findElement]
    Then PAUSE_INSEC <sleepInSec>
    Examples:
    |sleepInSec|
    |2      |




