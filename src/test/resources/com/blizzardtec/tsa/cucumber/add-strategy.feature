Feature: Add a new strategy

As a trader
I want to add a new trading strategy
so that I can analyse it

@addstrategy
Scenario: Add valid strategy
    Given I enter several rules
    When I save the new strategy
    Then I can analyse the new strategy