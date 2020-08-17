FROM openjdk:13-jdk-alpine3.10

EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY target/libs libs
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]