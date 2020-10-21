Feature: JPet Store Application

  Scenario:  Purchase pet animals on the Jpet Store WebPage
    Given that I OPEN [url]
    When I CLICK [enter_store] link
    Then I should SEE [sign_in] text
    When I CLICK [fish_link] link
    Then I should SEE [fish_h2] text
    When I CLICK [angel_fish_link] link
    Then I should SEE [angel_fish_h2] text
    When I CLICK [large_angel_fish_link] link
    Then I should SEE [large_angle_fish_text] text

