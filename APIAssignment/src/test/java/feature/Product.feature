Feature: Test Product List with Rest Assured Library and Cucumber Framework

  Scenario: Get Product List
    Given enter url and and get product list
    When  hit GET request get product list
    Then  validate status code for GET product list
    Then   validate content for GET product list
    And   validate length for GET product list