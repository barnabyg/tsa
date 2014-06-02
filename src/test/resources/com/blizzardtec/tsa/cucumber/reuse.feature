Feature: Reuse existing data sets

As a trader
I would like to reuse data sets
so that I do not have to load them again for every test

Scenario: Reuse a loaded data set with a different strategy
    Given I have uploaded a data set
    And I have uploaded a trading strategy
    And I have analysed the trading strategy with the data set
    When I upload a new trading strategy
    Then I can reuse the same data set to analyse the new trading strategy
