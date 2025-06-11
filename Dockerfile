


FROM node:18 AS tailwind-builder

WORKDIR /app
COPY src/main/frontend/ ./src/main/frontend/

WORKDIR /app/src/main/frontend

RUN npm install
RUN npm run build



FROM eclipse-temurin:17 AS builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src ./src

COPY --from=tailwind-builder /app/src/main/resources/static/main.css ./src/main/resources/static/main.css


RUN ./mvnw clean package -DskipTests

# Choisit une image de base avec java 23 préinstallé
FROM eclipse-temurin:23-jdk
WORKDIR /app

COPY --from=builder /app/target/pharmapro-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]


