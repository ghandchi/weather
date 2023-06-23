FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/weather
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install

FROM eclipse-temurin:17-jre-jammy
RUN addgroup inpress; adduser --ingroup inpress --gecos '' --disabled-password askar
USER askar
WORKDIR /opt/weather
COPY --from=builder /opt/weather/target/*.jar /opt/weather/
ENTRYPOINT ["java", "-jar", "/opt/weather/weather.jar"]


# in current folder:
# docker build -t inpress/weather:1.0.0 .

# or in parent folder:
# docker build -t inpress/weather:1.0.0 weather