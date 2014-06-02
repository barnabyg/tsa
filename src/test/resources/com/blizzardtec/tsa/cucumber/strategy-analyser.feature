Feature: Test a trading strategy

As a trader
I want to encode my trading strategy
so that I can evaluate its effectiveness

@analysis
Scenario: Encode a simple strategy with rising data
    Given I upload a "rising" strategy that "buys" when the "rolling 5 day average" is "positive"
    And I upload a dataset
    When the strategy is evaluated
    Then only buy events will occur

@analysis
Scenario: Encode a simple strategy with falling data
    Given I upload a "falling" strategy that "sells" when the "rolling 5 day average" is "negative"
    And I upload a dataset
    When the strategy is evaluated
    Then no buy events will occur
