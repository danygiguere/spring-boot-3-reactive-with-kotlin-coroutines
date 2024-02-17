# Spring boot 3 reactive with Kotlin coroutines and r2dbc

### About
- This project is just a demo or a starting template to work with Spring boot 3 with Kotlin coroutines and r2dbc and DatabaseClient (https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- The project is set up to work with the H2 in-memory but you can comment the h2 config and uncomment the mysql config in the application.yml file if you prefer to work with mysql (you can clone this project to create a dockerized db: https://github.com/danygiguere/docker_db)
- If you are using IntelliJ Ultimate, you can run the requests.http file (in the test folder) to test all the available routes
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

### TLDR 
- Clone the project, run it (it's set up by default with H2), then go to /test/requests.http and run the requests one by one.

**********
# Note regarding this branch.
Docker is working on this branch but I had to comment the H2DatabaseConfiguration script else the containters (app and db) would not connect
But now, I can start the docker_db container and then with the instructions below
https://www.jetbrains.com/help/idea/run-and-debug-a-spring-boot-application-using-docker-compose.html
I can start the app, do some changes, and then just press the green play button as usual to restart the app.
To us the debugger, intelliJ will give the instructions in the logs but basicall we need to go in Docker Desktop, then Resources / File sharing and add the path specified in the logs (on Mac it's the Applications folder)
**********



