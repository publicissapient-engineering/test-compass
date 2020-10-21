Feature: Unselect Command Feature
  Scenario: UnSelect Command Scenario Yatra booking
    Given I OPEN [yatraUrl]
    And I CLICK [nonStopFlightCheckBox]
    And I UNSELECT [nonStopFlightCheckBox]