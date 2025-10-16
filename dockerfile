FROM openjdk:17-jdk-alpine AS build
COPY ./target/final-project-0.0.1-SNAPSHOT.jar java-app.jar
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar java-app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "java-app.jar"]