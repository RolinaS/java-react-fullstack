# Étape build: Maven + JDK 21
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copier pom et sources
COPY pom.xml /workspace/pom.xml
COPY src /workspace/src

# Construire le WAR (sans étape go-offline)
RUN --mount=type=cache,target=/root/.m2 mvn -U -B -DskipTests package

# Exposer le résultat dans /dist via étape d'exécution (géré par docker-compose)
CMD ["/bin/sh", "-c", "cp -f target/app.war /dist/app.war && echo 'WAR exported to /dist/app.war'"] 