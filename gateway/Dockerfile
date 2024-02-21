FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /gateway

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

COPY target/gateway-1.0.0.jar gateway.jar

CMD /wait && java -jar gateway.jar
