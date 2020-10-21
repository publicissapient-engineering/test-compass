Feature: SELECT NODE FROM TREE Feature
  Scenario Outline: SELECT NODE FROM TREE Scenario
    Given I OPEN [url]
    Then PAUSE_INSEC <sleepInSec>
    Then SELECT_NODE_FROM_TREE [xpathForParentNode]
    Then PAUSE_INSEC <sleepInSec>
    Examples:
    |sleepInSec|
    |2         |

  Scenario Outline: SELECT NODE FROM TREE Scenario
    Given I OPEN [url]
    Then PAUSE_INSEC <sleepInSec>
    Then SELECT_NODE_FROM_TREE [xpathForParentNode1]
    Then PAUSE_INSEC <sleepInSec>
    Examples:
      |sleepInSec|
      |2         |




