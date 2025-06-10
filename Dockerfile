

# Choisit une image de base avec java 23 préinstallé
FROM eclipse-temurin:23-jdk
# eclipse-temurin est un JDK maintenu et compatible avec spring


# Définit le dossier de travail dans le conteneur
WORKDIR/app
# Cela signifie que toutes les prochaines commandes s'exécutent dans /app


# copie les fichiers Maven dans le contenuer
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
# copie les fichiers necessaires pour que Maven télécharge les dépendances meme sans internet dans le conteneur

# copie le code source
COPY src ./src
# place le code dans le conteneur Docker

# compile le projet sans lancer les tests
RUN ./mvnw package -DskipTests
# crée le fichier .jar final.

# Démarre l'application avec le jar généré
CMD ["java","-jar","target/pharmapro-0.0.1-SNAPSHOT.jar"]
# Démarre l'application quand Docker exécute l'image