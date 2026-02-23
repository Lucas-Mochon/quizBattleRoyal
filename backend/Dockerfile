FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar application.jar

EXPOSE 8080 8081 8082 8083 8761

ENTRYPOINT ["sh", "-c", "java -jar application.jar"]
