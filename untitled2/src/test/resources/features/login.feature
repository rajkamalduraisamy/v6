Feature: User Login

  Background:
    Given the user is on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When the user enters username from config
    And the user clicks the login button
    Then the dashboard should be displayed

  @regression
  Scenario: Login fails with invalid credentials
    When the user enters username "admin" and password "wrongpass"
    And the user clicks the login button
    Then an error message "Invalid credentials" should be displayed
