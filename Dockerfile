
FROM node:18 as node-builder
WORKDIR /app
COPY frontend/ ./frontend/
RUN cd frontend && npm install && npm run build

# etape java
FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY --from=node-builder /app/frontend/../src/main/resources/static/css/style.css ./src/main/resources/static/css/style.css
COPY . .
RUN ./mvnw clean package -DskipTests

# Choisit une image de base avec java 23 préinstallé
FROM eclipse-temurin:23-jdk
# eclipse-temurin est un JDK maintenu et compatible avec spring


# Définit le dossier de travail dans le conteneur
WORKDIR /app
# Cela signifie que toutes les prochaines commandes s'exécutent dans /app


# copie les fichiers Maven dans le contenuer
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
# copie les fichiers necessaires pour que Maven télécharge les dépendances meme sans internet dans le conteneur

# compile le projet sans lancer les tests
RUN ./mvnw package -DskipTests
ENTRYPOINT ["java","-jar","target/pharmapro-0.0.1-SNAPSHOT.jar"]
# crée le fichier .jar final.

# copie le code source
COPY src ./src
# place le code dans le conteneur Docker



EXPOSE 8080

# Démarre l'application avec le jar généré
CMD ["java","-jar","target/pharmapro-0.0.1-SNAPSHOT.jar"]
# Démarre l'application quand Docker exécute l'image
