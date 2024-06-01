# Étape de construction
FROM maven:3.8.3-openjdk-17 AS build

# Répertoire de travail
WORKDIR /app

# Copier les fichiers de projet
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Étape finale (runtime)
FROM openjdk:17-jdk-slim

# Répertoire de travail
WORKDIR /app

# Copier le jar de l'étape de construction
COPY --from=build /app/target/bibliotheque-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de l'application
EXPOSE 8081

# Commande de démarrage de l'application
CMD ["java", "-jar", "app.jar"]