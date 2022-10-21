Feature: Baidu search

  Scenario Outline: Baidu search
    Given Visit Baidu website
    When Input "<content>"
    Then Should be to see the "<content>"
    Examples:
      | content |
      | BDD     |