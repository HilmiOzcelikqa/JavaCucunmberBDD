Feature: user login
  @login
  Scenario: login with correct credentials
    Given User fills the username and password fields with correct credentials
    Then User logins successfully
