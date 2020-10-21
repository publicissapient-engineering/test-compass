Feature: This is a sample login testing feature

  Scenario: Login scenario into gmail
    Given that I OPEN [url]
    And I ENTER [username]
    And I ENTER [password]
    When I CLICK [submit]
    Then I should SEE [dashboard]
