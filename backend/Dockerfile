#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build

RUN mkdir app
COPY pom.xml /app
RUN mvn -f /app/pom.xml dependency:resolve

COPY src /app/src
RUN mvn -f /app/pom.xml package

#
# Package stage
#
FROM alpine:3.14.2
RUN apk add openjdk11-jre
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-Dserver.port=8081","-jar","app.jar"]