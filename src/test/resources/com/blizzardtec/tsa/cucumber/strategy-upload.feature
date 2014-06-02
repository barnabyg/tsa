Feature: Upload a trading strategy

As a trader
I want to upload a trading strategy
so that I can analyse it

@strategy
Scenario: Load a trading strategy
    Given a strategy file
    When the strategy file is loaded
    Then the strategy is stored
