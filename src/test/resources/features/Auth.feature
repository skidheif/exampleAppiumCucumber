@smoke

Feature: Auth features

  Scenario Outline: validate search results

    When User click ok button
    When User fill "<login>" the email field
    When User fill "<pass>" the password field
    When User click sign in button
    Then User waiting "10" seconds

    Examples:
      | login          | pass       |
      | 1234           | 1234       |
