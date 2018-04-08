Feature: ChangeService Change Response
  I want to use ChangeService Gives the Right Change Response

  @Optimal
  Scenario: Optimal Change
    Given I want change for 576
    And I choose Optimal change
    When I call ChangeService for optimal change
    Then I validate the Optimal outcomes

  @Supply
  Scenario: Supply Change
    Given I want change for 576
    And I choose Supply change
    When I call ChangeService for supply change
    Then I validate the Supply outcomes
