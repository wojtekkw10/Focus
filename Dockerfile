#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build

COPY pom.xml /app
RUN mvn -f /app/pom.xml package

COPY src /app/src

#
# Package stage
#
FROM alpine
RUN apk add openjdk11-jre
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]