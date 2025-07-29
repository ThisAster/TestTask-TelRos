# Используем базовый образ с JDK 17
FROM maven:3.9.8-eclipse-temurin-17 AS build


# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /test-telros

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /test-telros

# Копируем JAR файл в контейнер
COPY --from=build /test-telros/target/test-telros-0.0.1-SNAPSHOT.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]