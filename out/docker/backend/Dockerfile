FROM openjdk:17-alpine
WORKDIR /workspace
COPY ./target/*.jar /workspace/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
