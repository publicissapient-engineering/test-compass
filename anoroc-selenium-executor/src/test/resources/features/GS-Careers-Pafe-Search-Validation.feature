Feature: Search on google

  Scenario Outline: Successful search for publicis sapient on google
    Given that I am OPEN [url]
    And I ENTER <search_term> in the text box
    And I CLICK [AnyKeyword] button
    And I SELECT <Division> option
    And I SELECT <Location> in the text box
    And I SELECT <Level> option
    And I CLICK [Submit] button
    Then I VERIFY <searchResults>
    Examples:
      | search_term  |Division| Location|Level|searchResults|
      | Java       |Engineering | IN|Associate|Search Results|
      | Automation       |Engineering | IN|Associate|Search Results|
