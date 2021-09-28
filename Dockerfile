FROM openjdk:17-jdk-alpine

COPY target/*.jar /app.jar

RUN java -jar app.jar