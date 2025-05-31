# Spring boot 3 reactive with Kotlin coroutines and r2dbc

### About
- This project is just a demo or a starting template to work with Spring boot 3 with Kotlin coroutines and r2dbc and DatabaseClient (https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- The project is set up to work mysql (you can clone this project to create a dockerized db: https://github.com/danygiguere/docker_db)
- I have kept the H2 database configuration in the project, but it has discrepancies compoare to the r2dbc:mysql db.
- If you are using IntelliJ Ultimate, you can run the requests.http file (in the test folder) to test all the available routes
- In this demo I haven't used an orm. If you'd like to use an ORM, have a look at CoroutineCrudRepository. It' is a non-blocking reactive interface for generic CRUD operations using Kotlin Coroutines.
- The app demonstrates:
  - how to create controllers, dtos, entities, and repositories with Kotlin coroutines
  - how to set up Flyway to manage migrations (it works with mysql but I wasn't able to make it work with h2)
  - how to set an exception handler to manage failed validations in a reactive context
  - how to return translated validation error messages (check the requests.http file for more info)
  - how to create custom validators
  - how to do a oneToMany relationship query
  - how to do a belongsTo relationship query
  - how to do a hasManyThrough relationship query
  - how to run suspend functions in parallel with async/await
  - how to create unit tests in a reactive context
  - how to do webclient calls with Kotlin Coroutines
  - how to data factories to seed the db and to create dtos in tests
  - how to set up Docker (I figured it out on the Docker branch (https://github.com/danygiguere/spring-boot-3-reactive-with-kotlin-coroutines/tree/Docker) but the H2DatabaseConfiguration script is problematic)
- I recommend you watch this nice video regarding coroutines and the different strategies available: https://www.youtube.com/watch?v=ahTXElHrV0c


### TLDR 
- Clone the project, run it (it's set up by default with H2), then go to /test/requests.http and run the requests one by one.
- There is a automatic seed on start up because of this config in application.yml: spring.profiles.active=seed.