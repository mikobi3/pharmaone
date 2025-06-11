# Étape 1 : Build Tailwind
FROM node:18 AS tailwind-builder

WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install

COPY frontend/. ./
RUN chmod +x ./node_modules/.bin/tailwindcss
RUN npm run build

# Étape 2 : Build Spring Boot app
FROM maven:3.9.6-eclipse-temurin-21 AS maven-builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src ./src
COPY --from=tailwind-builder /app/frontend/src/main/resources/static/main.css ./src/main/resources/static/main.css

RUN ./mvnw clean package -DskipTests

# Étape 3 : Image finale légère
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=maven-builder /app/target/pharmapro-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]



