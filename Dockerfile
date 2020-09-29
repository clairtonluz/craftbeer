FROM openjdk:8-jdk AS builder
WORKDIR /app
ADD . .
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:8-jre
LABEL author="Clairton Luz <clairton.c.l@gmail.com>"
WORKDIR /app
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 9000
