#
# Build stage
#  docker-compose -f cliente-docker-compose.yml up -d
#
FROM maven:3.6.3-openjdk-14-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package Stage
#
FROM openjdk:14-jdk-alpine
EXPOSE 8080
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

