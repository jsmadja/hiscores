FROM openjdk:15-jdk-alpine3.12

RUN apk update && apk add ttf-dejavu

EXPOSE 8080

ARG JAR_FILE=hiscores/*.jar
COPY public public
COPY hiscores/libs libs
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar", "-Djava.awt.headless", "true"]