FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /feedback-service

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

COPY target/feedback-service-1.0.0.jar feedback-service.jar

CMD /wait && java -jar feedback-service.jar
