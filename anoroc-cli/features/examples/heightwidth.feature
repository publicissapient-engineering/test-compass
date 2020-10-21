Feature: SCROLL CommandS Feature
  Scenario Outline: SCROLL DOWN Scenario
    Given I OPEN [url]
    Then PAUSE_INSEC <sleepInSec>
    Then check WIDTH <pixelWidth>
    Examples:
    |pixelWidth|sleepInSec|
    |200       |2         |




