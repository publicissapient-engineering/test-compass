Feature: ValidateTitle Command Feature
 Scenario Outline: ValidateTitle command Scenario outline
    Given I OPEN [url]
    Then  VALIDATE_TITLE <TextToVerify>
    When I ENTER <Text>
    And I CLICK [searchButton]
    And ENTER_APPEND <AppendText>
    And CLICK [searchButton1]
   Examples:
   | TextToVerify|Text|AppendText|
   |Google|Sapient    | Publicis |