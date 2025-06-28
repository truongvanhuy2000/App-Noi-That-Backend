FROM truongvanhuy2000/appnoithatbackendbase AS build
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B package --file pom.xml -DskipTests

FROM openjdk:17-alpine
WORKDIR /workspace
COPY --from=build /workspace/target/*.jar /workspace/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
