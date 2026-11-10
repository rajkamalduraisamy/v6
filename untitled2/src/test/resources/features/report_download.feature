Feature: Report Download

  Background:
    Given the user is logged in using config credentials

  @smoke
  Scenario: Download a PDF report successfully
    When the user navigates to the Reports section
    And selects report type "PDF"
    And clicks the Download button
    Then a download success message should be displayed
    And the report file should exist in the downloads folder
