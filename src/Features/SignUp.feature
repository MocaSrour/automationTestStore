@tag
Feature: Test SignUp functionality

  @tag1
  Scenario: Check sign up is successful with valid credetials
    Given browser is open
    And user is on sign up page
    When user enters all feilds
    And user clicks sign up button or continue
    Then user is navigated to home page