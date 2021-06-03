# Currency conversion application

The application is a currency conversion service which uses external providers for currency rates. 
It uses random provider from the pool and switches to another if the previous failed. 
Solution uses simple internal caching to save rates in memory temporarily (default is 1 minute).   

You can check functionality online (deployed to Heroku) by sending requests via deployed [Swagger](https://nikita-currency-conversion.herokuapp.com/swagger-ui/#/)

### Local build

Requires Java 11 installed 

`./gradlew clean bootRun`

[Then just open Swagger to test the endpoint](http://localhost:8080/swagger-ui/#/currency-conversion-controller/validateAddressUsingPOST)

### What to improve

* Caching. Internal caching is implemented using simple in memory solution. 
  External caching may be added using tools like Hazelcast.
* Upgrade to next subscription plan for api.exchangeratesapi.io provider in order to support SSL and unlock other base currencies except EUR.
* Containerize application with Docker or alternative.
* Hide credentials (access key) and move them to any vault.
* Use code analysis tools like Checkstyle, PMD, etc.
