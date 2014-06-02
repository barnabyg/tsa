Feature: Load historic equity data

As a trader
I want to load a historic data set
so that I can use the data to test a trading strategy

@data
Scenario: Load one weeks worth of daily price information
    Given a file containing 5 days worth of data
    When the file is loaded
    Then 5 days worth of data is stored
