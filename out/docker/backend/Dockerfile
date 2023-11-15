FROM maven:3.8.5-openjdk-17-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B package --file pom.xml -DskipTests

FROM openjdk:17-alpine
WORKDIR /workspace
COPY --from=build /workspace/target/*.jar /workspace/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
