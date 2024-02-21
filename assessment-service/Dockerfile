FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /assessment-service

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

COPY target/assessment-service-1.0.0.jar assessment-service.jar

CMD /wait && java -jar assessment-service.jar
