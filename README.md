# Spring boot 3 reactive with Kotlin coroutines and r2dbc

**********
# Note regarding this branch.
- Docker is working on this branch but I had to comment the H2DatabaseConfiguration script else the containters (app and db) would not connect.
- But now, I can start the docker_db container and then with the instructions below:
  https://www.jetbrains.com/help/idea/run-and-debug-a-spring-boot-application-using-docker-compose.html
- I can start the app, do some changes, and then just press the green play button as usual to restart the app.
- To use the debugger, intelliJ will give the instructions in the logs but basically we need to go in Docker Desktop, then Resources / File sharing and add the path specified in the logs (on Mac it's the Applications folder)
- The only thing I will need to figure out is how to set a fix port because IntellJ keeps changing the port every time I start the app (it seems to be a know but according to https://youtrack.jetbrains.com/issue/IDEA-284458/DockerCompose-target.-Port-bindings-from-docker-compose-file-are-absent-in-the-target-container?_gl=1*ku2d29*_ga*MTgzMTg0OTA3My4xNzA2NjU2NTEx*_ga_9J976DJZ68*MTcwODE5MTA5NC4xMi4xLjE3MDgxOTQzODIuMzQuMC4w&_ga=2.213165588.126016512.1708128656-1831849073.1706656511)
**********


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
  - how to create unit tests in a reactive context
  - how to do webclient calls with Kotlin Coroutines
  - how to data factories to seed the db and to create dtos in tests
  - how to set up Docker (I figured it out on the Docker branch (https://github.com/danygiguere/spring-boot-3-reactive-with-kotlin-coroutines/tree/Docker) but the H2DatabaseConfiguration script is problematic)

### TLDR 
- Clone the project, run it (it's set up by default with H2), then go to /test/requests.http and run the requests one by one.

### Todo
- add validation to block users from updating a post that doesn't belong to them
- setLocal early in the request lifecycle so that I can get it anytime with getLocale (it's already setin by default for the validations)