FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /config-srv

COPY target/config-srv-1.0.0.jar config-srv.jar

CMD ["java", "-jar", "config-srv.jar"]
