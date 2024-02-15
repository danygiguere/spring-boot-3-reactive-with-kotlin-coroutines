FROM eclipse-temurin:21
WORKDIR /app
EXPOSE 8080
ADD ./build/libs/demo-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]