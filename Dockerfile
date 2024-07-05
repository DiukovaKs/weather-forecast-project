FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /target/demo1-0.0.1-SNAPSHOT.jar /app/weather-bot.jar
ENTRYPOINT ["java", "-jar", "weather-bot.jar"]