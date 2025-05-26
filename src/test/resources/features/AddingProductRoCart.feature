Feature: Adding a product to the cart
  Background:
    Given User fills the username and password fields with correct credentials
    Then User logins successfully
    @addtocart
    Scenario: Add Correct Product To The Cart
      Given User searchs for product
      And User arrange price filter
      And User choose the product at the bottom of list and go to product details
      And User choose the product which belongs to the worst rated seller and adds to the cart
      Then User checks if the correct product added to the cart

