Feature: This is a sample login testing feature

  Scenario Outline: Login scenario into gmail
    Given that I OPEN [url]
    And I ENTER <username> <password>
    And I ENTER <password>
    When I CLICK [submit]
    Then I should SEE [dashboard]
    Examples:
      |   username        |   password      |
      |   venki@ps.com    |  password1      |
      |   raghu@ps.com    |  password2      |
