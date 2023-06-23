FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/gateway
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install

FROM eclipse-temurin:17-jre-jammy
RUN addgroup inpress; adduser --ingroup inpress --gecos '' --disabled-password askar
USER askar
WORKDIR /opt/gateway
EXPOSE 8080
COPY --from=builder /opt/gateway/target/*.jar /opt/gateway/
ENTRYPOINT ["java", "-jar", "/opt/gateway/gateway.jar"]


# in current folder:
# docker build -t inpress/gateway:1.0.0 .

# or in parent folder:
# docker build -t inpress/gateway:1.0.0 gateway