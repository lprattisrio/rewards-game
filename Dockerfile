FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/rewards-game-0.1.0.jar /app/rewards-game.jar

COPY src/main/resources/config.json ./config.json

#ENTRYPOINT ["java", "-jar", "rewards-game.jar"]

CMD ["java", "-jar", "./rewards-game.jar", "--config", "./config.json", "--betting-amount", "10"]
