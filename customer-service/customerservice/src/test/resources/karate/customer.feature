Feature: Customer API

Scenario: Get Customers

Given url 'http://localhost:8081/auth/login'

And request
"""
{
  "username":"aasif",
  "password":"Aasif@786"
}
"""

When method post

Then status 200

* def token = response.token

Given url 'http://localhost:8081/api/customers'

And header Authorization = 'Bearer ' + token

When method get

Then status 200

Scenario: Unauthorized Access

Given url 'http://localhost:8081/api/customers'

When method get

Then status 401