Feature: Google search feature
  Background:
    * def params = read("search.json")
    * configure driver = { type: 'geckodriver', start: false, webDriverUrl: 'http://localhost:4444/wd/hub' }

  Scenario: Successful search for some terms on google
    Given driver params.data.url
    And input(params.data.inputXpath,params.data.inputText)
    When submit().click(params.data.submitButton)
    Then waitForUrl(params.data.newUrl)

