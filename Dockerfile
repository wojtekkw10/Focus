FROM openjdk:11

COPY target/*.jar /app.jar

RUN java -jar app.jar