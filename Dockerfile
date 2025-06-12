# Étape 1 : Build du CSS avec Tailwind
FROM node:18 AS tailwind-builder

WORKDIR /frontend
COPY src/main/frontend/package*.json ./
RUN npm install

COPY src/main/frontend ./
RUN chmod +x ./node_modules/.bin/tailwindcss
RUN npm run build

# Étape 2 : Build de l'application Java
FROM maven:3.9.6-eclipse-temurin-21 AS maven-builder

WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

COPY --from=tailwind-builder /frontend/../src/main/resources/static/main.css ./src/main/resources/static/main.css
RUN ./mvnw clean package -DskipTests

# Étape 3 : Image finale pour exécution
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=maven-builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]




