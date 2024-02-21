FROM maven:3.8-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM eclipse-temurin:17-jdk-alpine as runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
EXPOSE 8080
