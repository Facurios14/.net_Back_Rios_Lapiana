# ----- 1. Etapa de "Build" (Construccion) -----
# Usamos una imagen de Java 17 (JDK) para compilar el proyecto
FROM eclipse-temurin:17-jdk-jammy AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiamos el código fuente de la aplicación
COPY src src

# Damos permisos de ejecución al wrapper de Gradle
RUN chmod +x ./gradlew

# Corremos el comando de Gradle para crear el .jar
# "bootJar" es la tarea específica de Spring Boot
RUN ./gradlew bootJar --no-daemon

# ----- 2. Etapa de "Runtime" (Ejecución) -----
# Usamos una imagen de Java 17 (JRE) que es más ligera
FROM eclipse-temurin:17-jre-jammy

# Establecemos el directorio de trabajo
WORKDIR /app

# Exponemos el puerto 8080 (el que usa Spring Boot por defecto)
EXPOSE 8080

# Copiamos SÓLO el .jar compilado desde la etapa "builder"
COPY --from=builder /app/build/libs/*.jar app.jar

# El comando para iniciar la aplicación cuando se corra el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]