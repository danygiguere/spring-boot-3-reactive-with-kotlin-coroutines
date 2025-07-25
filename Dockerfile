FROM eclipse-temurin:21
WORKDIR /app
EXPOSE 8080
ADD ./build/libs/spring-boot-3-reactive-with-kotlin-coroutines-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "spring-boot-3-reactive-with-kotlin-coroutines-0.0.1-SNAPSHOT.jar"]