Feature: Visualise the results of a test run

As a trader
I want to visualise the results of a test
so that I can evaluate the effectiveness of a trading strategy

@visualise
Scenario: Show a chart of account balance over 5 days
    Given I upload a standard dataset
    And I upload a trading strategy
    And the trading strategy has been processed against the data
    When visualise results is selected
    Then a chart of the results will be shown
