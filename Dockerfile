
FROM node:18 AS tailwind-builder

WORKDIR /app

COPY frontend/ ./frontend/
WORKDIR /app/frontend

RUN npm install && npm run build

# etape java
FROM eclipse-temurin:23-jdk AS builder

WORKDIR /app

# copie les fichiers Maven dans le contenuer
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# copie de code source
COPY src ./src

COPY --from=tailwind-builder /app/frontend/build/style.css  ./src/main/resources/static/css/style.css


RUN ./mvnw clean package -DskipTests

# Choisit une image de base avec java 23 préinstallé
FROM eclipse-temurin:23-jdk
# eclipse-temurin est un JDK maintenu et compatible avec spring


# Définit le dossier de travail dans le conteneur
WORKDIR /app

COPY --from=builder /app/target/pharmapro-0.0.1-SNAPSHOT.jar

# Cela signifie que toutes les prochaines commandes s'exécutent dans /app
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]


