FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/discovery
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install

FROM eclipse-temurin:17-jre-jammy
RUN addgroup inpress; adduser --ingroup inpress --gecos '' --disabled-password askar
USER askar
WORKDIR /opt/discovery
EXPOSE 8761
COPY --from=builder /opt/discovery/target/*.jar /opt/discovery/
ENTRYPOINT ["java", "-jar", "/opt/discovery/discovery.jar"]


# in current folder:
# docker build -t inpress/discovery:1.0.0 .

# or in parent folder:
# docker build -t inpress/discovery:1.0.0 discovery
