# Spring boot 3 reactive with Kotlin coroutines

### About
- This project is just a demo or a starting template to work with Spring boot and Kotlin coroutines
- The project is set up to work with the H2 in-memory but you can comment the h2 config and uncomment the mysql config in the application.yml file if you prefer to work with mysql (you can clone this project to create a dockerized db: https://github.com/danygiguere/docker_db)
- If you are using IntelliJ Ultimate, you can run the requests.http file (in the test folder) to test all the available routes
- The app demonstrates:
  - how to create controllers, dtos, entities, and repositories with Kotlin coroutines
  - how to set up Flyway to manage migrations
  - how to set an exception handler to manage failed validations in a reactive context
  - how to use input request validations with translations (check the requests.http file for more info)
  - how to create custom validator

### Todo
- do an async parallel demo (findByIdWithPosts should be async)
- create Image model
- create belongsTo and hasManyThrough relationships queries
- create a web client call

- find a way to seed the db (maybe with a cli)
- setLocal early in the request lifecycle so that I can get it anytime with getLocale (it's already set by default for the validations)




