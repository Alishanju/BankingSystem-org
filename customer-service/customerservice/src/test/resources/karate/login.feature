Feature: Login API

Scenario: Successful Login

Given url 'http://localhost:8081/auth/login'

And request
"""
{
  "username":"Nemalu",
  "password":"Nemalu@786"
}
"""

When method post

Then status 200

And match response.token != null

And match response.user.username == "Nemalu"

And match response.user.role == "USER"

And match response.user.id == 11