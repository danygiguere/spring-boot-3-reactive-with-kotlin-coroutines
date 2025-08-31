# Spring boot 3 reactive with Kotlin coroutines and r2dbc

## About
- This project is just a demo or a starting template to work with Spring boot 3 with Kotlin coroutines and r2dbc and DatabaseClient (https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- The project is set up to work mysql (you can clone this project to create a dockerized db: https://github.com/danygiguere/docker_db)
- If you are using IntelliJ Ultimate, you can run the requests.http file (in the test folder) to test all the available routes
- In this demo I haven't used an orm. If you'd like to use an ORM, have a look at CoroutineCrudRepository. It' is a non-blocking reactive interface for generic CRUD operations using Kotlin Coroutines.
- I recommend you watch this video regarding coroutines : https://www.youtube.com/watch?v=ahTXElHrV0c

### The app demonstrates:
- how to create controllers, dtos, entities, and repositories with Kotlin coroutines
- how to set up Flyway to manage migrations
- how to set an exception handler to manage failed validations
- how to return translated validation error messages
- how to create custom validators
- how to set up the Security with JWT with an httpOnly cookie
- how to use webfilters to block user from updating data that doesn't belong to them (checkPostOwnershipWebFilter)
- how to do a oneToMany relationship query
- how to do a belongsTo relationship query
- how to do a hasManyThrough relationship query
- how to run suspend functions in parallel with async/await
- how to create unit tests in a reactive context
- how to do webclient calls with Kotlin Coroutines
- how to create Fixtures and factories to seed the db, and to create dtos data for tests
- how to run the project in a Docker container using docker-compose (you need to make a copy of .env.example and rename the copy to .env, add your db credentials and then run docker-compose up)

### Installation
- Add your credentials in the application-secrets.yml file
- Start the project with ./gradlew bootRun or ./gradlew bootRun -Dspring.profiles.active=local

### Deployment
- Look at the README_AWS.MD file for instructions on how to deploy the project to AWS ECS with Fargate and an Application Load Balancer