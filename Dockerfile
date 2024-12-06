FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/SpringBootTgBotProject.jar tgbot.jar
ENTRYPOINT ["java", "-jar", "tgbot.jar"]