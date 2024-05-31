# immagine base con JDK e Maven per compilare il progetto
FROM maven:3.8.4-openjdk-17-slim AS build

# Copia il codice sorgente dei progetti "authentication" e "museo" nella directory /app
COPY authentication /app/authentication
COPY museum /app/museum

# Compila entrambi i progetti
WORKDIR /app/authentication
RUN mvn clean install

WORKDIR /app/museum
RUN mvn clean install

# Crea l'immagine finale con i file JAR dei progetti "authentication" e "museo"
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/authentication/target/authentication-1.0.0.jar /app/authentication.jar
COPY --from=build /app/museum/target/museum-1.0.0.jar /app/museum.jar


EXPOSE 4000

# Avvia l'applicazione "museo"
CMD ["sh", "-c", "sleep 800 && java -jar authentication.jar && java -jar museum.jar"]


