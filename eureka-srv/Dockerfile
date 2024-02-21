FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /eureka-srv

COPY --from=ghcr.io/ufoscout/docker-compose-wait:latest /wait /wait

COPY target/eureka-srv-1.0.0.jar eureka-srv.jar

CMD /wait && java -jar eureka-srv.jar
