FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /client-ui

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

COPY target/client-ui-1.0.0.jar client-ui.jar

CMD /wait && java -jar client-ui.jar
