# Etapa de construcción (Build) con Gradle
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
# Aseguramos permisos de ejecución y formato Unix para gradlew
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
# Construimos el jar saltando tests
RUN ./gradlew clean build -x test

# Etapa de ejecución (Run)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Copiamos el jar desde la carpeta de build de Gradle
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]