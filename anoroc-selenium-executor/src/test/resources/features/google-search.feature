Feature: Search on google

  Scenario Outline: Successful search for publicis sapient on google
    Given that I am OPEN [url]
    And I ENTER <search_term> in the text box
    When I CLICK [search_button]
    Then I should SEE [search_results]
    Examples:
      | search_term |
      | hello       |

