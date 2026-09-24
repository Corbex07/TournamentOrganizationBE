# Keep this until proper CD is built, after that this part is redundant
FROM eclipse-temurin:25-jdk-jammy AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew # Makes gradle wrapper an executable

COPY src src

RUN ./gradlew bootJar -x test --no-daemon # Creates the build that can be copied


FROM eclipse-temurin:25-jre-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN useradd --system --create-home appuser \
    && chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]