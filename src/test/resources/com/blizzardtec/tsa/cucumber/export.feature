Feature: Export results

As a trader
I want to export my test results
so that I can analyse them outside of the application

Scenario: Export to csv
    Given I have a result set
    When I select export
    Then a csv file will be generated from the results
