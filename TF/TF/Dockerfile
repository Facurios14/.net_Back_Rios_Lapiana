# Etapa 1: construir el proyecto
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

# Copiamos todo el contenido del proyecto
COPY . .

# Construimos el .jar sin correr tests
RUN gradle clean build -x test

# Etapa 2: imagen final más liviana
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiamos el jar generado desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponemos el puerto configurado en application.properties
EXPOSE 8080

# Comando para ejecutar el backend
ENTRYPOINT ["java", "-jar", "app.jar"]