# ---- Etapa 1: compilar el proyecto con Maven + JDK 21 ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos primero solo el pom.xml para aprovechar el cache de Docker:
# si no cambian las dependencias, no las vuelve a descargar en cada build
COPY pom.xml .
RUN mvn dependency:go-offline

# Ahora copiamos el resto del codigo fuente y compilamos el jar
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Etapa 2: imagen final, solo con el jar ya compilado ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el jar generado en la etapa anterior (build), no el proyecto completo
COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto real por la variable de entorno PORT
EXPOSE 8080

# Arranca la aplicacion. -Dserver.port=$PORT hace que Spring Boot
# escuche en el puerto que Render le asigne (no siempre es 8080)
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
