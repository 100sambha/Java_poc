FROM openjdk:21-jdk
WORKDIR /app
COPY target/todoapp.jar todoapp.jar
ENTRYPOINT ["java", "-jar", "todoapp.jar"]