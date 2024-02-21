FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /patient-api

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

COPY target/patient-api-1.0.0.jar patient-api.jar

CMD /wait && java -jar patient-api.jar
