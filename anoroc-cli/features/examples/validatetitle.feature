Feature: ValidateTitle Command Feature
 Scenario Outline: ValidateTitle command Scenario outline
    Given I OPEN [url]
    Then  VALIDATE_TITLE <TextToVerify>
   Examples:
   | TextToVerify|
   |Google1|
