Feature: Test Product List with Rest Assured Library and Cucumber Framework

  @SmokeTest
  Scenario: Get Product List
    Given valid url to fetch products
    When  hit GET request get product list
    Then  validate status code for GET product list
    And   validate content for GET product list
    And   validate length for GET product list

  @SmokeTest
  Scenario: Post Product List
    Given valid url to fetch products
    When  add product to productList
    Then  the product should be POST in product list

  @SmokeTest
  Scenario: Put Brand List
    Given valid url to fetch products
    When  put a brand from brand list
    Then  the brand should be PUT in brand list