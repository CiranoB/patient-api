FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-noble

WORKDIR /app

COPY --from=build /app/target/hackathon-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 9292

CMD ["java", "-jar", "app.jar"]