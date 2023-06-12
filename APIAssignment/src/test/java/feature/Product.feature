Feature: Test Product List with Rest Assured Library and Cucumber Framework

  Scenario: Get Product List
    Given valid page number is available
    When  send request to get ProductList
    Then  validate status code
    And   validate content
    And   validate length