Feature: Test Product List with Rest Assured Library and Cucumber Framework

  Scenario: Post Product List
    Given enter url and and get product list
    When  validate status code for post product list
    Then   validate message for post product list