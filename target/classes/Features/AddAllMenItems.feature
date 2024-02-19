
@tag
Feature: Add all items to the cart in men section

  @tag1
  Scenario: Go to men section & add all items
    Given men section page is open
    When user add all items
    Then cart should contain all items