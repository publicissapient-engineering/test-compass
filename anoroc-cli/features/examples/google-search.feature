Feature: Search on google

  Scenario Outline: Successful search for some terms on google
    Given that I OPEN [url] to do search
    And I ENTER <search_term> in the text box
    When I CLICK [search_button] on the search page
    Then I should SEE [search_results] on the result page
    Examples:
      | search_term |
      | hello       |
      | hi          |
      | sapient     |
      | good one    |
      | india       |
