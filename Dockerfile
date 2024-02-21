FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app
COPY . .
RUN apk add --no-cache maven
RUN mvn clean package

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
EXPOSE 8080
