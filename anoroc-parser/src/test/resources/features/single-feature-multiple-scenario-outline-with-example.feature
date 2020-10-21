Feature: This is a sample login testing feature for gmail and yahoo

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

  Scenario Outline: Login scenario into yahoo
    Given that I OPEN [url]
    And I ENTER <username> <password>
    And I ENTER <password>
    When I CLICK [submit]
    Then I should SEE [dashboard]
    Examples:
      |   username        |   password      |
      |   venki@ps.com    |  password1      |
      |   raghu@ps.com    |  password2      |

  Scenario Outline: Invalid login scenario into yahoo
    Given that I OPEN [url]
    And I ENTER <username> <password>
    And I ENTER <password>
    When I CLICK [submit]
    Then I should SEE [error_message]
    Examples:
      |   username        |   password      |
      |   error@ps.com    |  errorpassword  |
