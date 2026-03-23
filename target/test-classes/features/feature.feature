Feature: Flight Booking 
 
  Scenario Outline: Search for a one-way flight
    Given User is on the ixigo homepage
    When user enters mob_no from <rowID> and click continue
    When User enters "Pune" as source and "Mumbai" as destination
    And User clicks on the search button
    Then Flight search results should be displayed
    When user clicks on BookBtn 
    
    Examples:
    |<rowID>|
    |1|
    
    
    

   
    