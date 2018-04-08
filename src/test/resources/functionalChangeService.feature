Feature: Functional ChangeService Change
  I want to check ChangeService Functionally

  @Functional
  Scenario: Functional Optimal Change
    Given I provide 576 for change
    When I call ChangeService for optimal change functional method
    Then I validate the Optimal result

  @Functional
  Scenario: Functional Supply Change
    Given I provide 2896 for change
    When I call ChangeService for supply change functional method
    Then I validate the Supply result
