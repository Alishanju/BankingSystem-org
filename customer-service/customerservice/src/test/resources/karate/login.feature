Feature: Login API

Scenario: Successful Login

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

And match response.token != null