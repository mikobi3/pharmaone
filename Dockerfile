# Étape 1 : Builder Tailwind CSS
FROM node:18 AS tailwind-builder

WORKDIR /app/src/main/frontend

# Copier les fichiers frontend
COPY src/main/frontend/package.json .
COPY src/main/frontend/package-lock.json .
COPY src/main/frontend/styles.css .
COPY src/main/frontend/tailwind.config.js .

# Installer Tailwind
RUN npm install

# Donner les droits à tailwindcss
RUN chmod +x ./node_modules/.bin/tailwindcss

# Créer le dossier de sortie pour le CSS
RUN mkdir -p ../resources/static

# Compiler Tailwind CSS
RUN npm run build


# Étape 2 : Builder Maven
FROM eclipse-temurin:23-jdk AS maven-builder

WORKDIR /app

# Copier les fichiers Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copier le reste du code
COPY src ./src

# Ajouter le CSS généré par Tailwind
COPY --from=tailwind-builder /app/src/main/resources/static/main.css ./src/main/resources/static/main.css

# Compiler l'application
RUN ./mvnw clean package -DskipTests


# Étape 3 : Image finale
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Copier le JAR généré
COPY --from=maven-builder /app/target/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]




