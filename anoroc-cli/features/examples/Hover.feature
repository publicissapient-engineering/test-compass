Feature: Testing Hover Feature
  Scenario: Hover scenario
    Given I OPEN [url]
    And I CLICK [closeButton]
    And mouse HOVER at [moreActionHover]
    And I CLICK [sellOnFlipkartLink]
