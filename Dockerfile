FROM eclipse-temurin:21-jdk-jammy
EXPOSE 8080
# Esta línea es la que falta o debe ser exacta:
COPY target/demopg-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]