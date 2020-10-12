Feature: Title of your feature
  I want to use this template for my feature file

 Scenario: Find City Geo Location based on Country Name
    When users provide city and country name
    And User enters "Germany" and "Frankfurt"
    Then the server should handle it and return a long and lati values