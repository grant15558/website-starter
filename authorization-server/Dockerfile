FROM gradle:8.14.3-jdk17-alpine AS build
WORKDIR /app
COPY . .
# Get keystore from s3 /certs/keystore.jks
RUN gradle clean build --no-daemon -x test

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/certs /certs
COPY --from=build /app/build/libs/*.jar app.jar
# password not being set via environment variable
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh
ENTRYPOINT ["/app/entrypoint.sh"]