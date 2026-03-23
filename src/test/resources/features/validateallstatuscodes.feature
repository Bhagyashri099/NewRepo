
Feature: API Error Status Code Validation
@ignore
 @skip_setup1
  Scenario: 404 Not Found - Missing Resource
    Given the API endpoint is "https://jsonplaceholder.typicode.com/users/5678900000"
    When I send a GET request
    Then the response status code should be 404
    @ignore
    @skip_setup1
  Scenario: Create multiple users and log results to Excel
    the API endpoint is "https://reqres.in/api/collections/users"
    When I send a POST request using data
   Then the response status code should be 401
    
     
     @ignore @skip_setup1
       Scenario: 400 error 
    Given the API endpoint is "https://reqres.in/api/users"
    When I send a POST with broken body
    Then the response status code should be 400
    
    @ignore @skip_setup1
     Scenario: 500 Internal server error  
    Given the API endpoint is "https://gorest.co.in/public/v2/users"
    When I send a GET request
    Then the response status code should be 500


