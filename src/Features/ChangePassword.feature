@tag
Feature: Change password
  @tag1
  Scenario: Change password
    Given Navigate to change password page
    When user input Current Password, New Password & press Continue
    Then Password should be changed
