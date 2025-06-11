


FROM node:18 AS tailwind-builder

WORKDIR /app
COPY src/main/frontend/ ./


ENV NODE_ENV=development

RUN npm install
RUN chmod +x ./node_modules/ ./bin/tailwindcss
RUN npm run build



FROM eclipse-temurin:17-jdk AS app-builder

WORKDIR /app
COPY . .


COPY --from=tailwind-builder /app/src/main/resources/static/main.css ./src/main/resources/static/main.css

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

# Choisit une image de base avec java 23 préinstallé
FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY --from=builder /app/target/pharmapro-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]


