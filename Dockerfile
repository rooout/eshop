# Stage 1: Build application using JDK
FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /src/advshop
COPY . .
RUN ./gradlew clean bootJar

# Stage 2: Create runtime environment using JRE
FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

# Define user and group
ARG USER_NAME=advshop
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

# Create user and group
RUN addgroup -g ${USER_GID} ${USER_NAME} \
  && adduser -h /opt/advshop -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

# Switch to non-root user
USER ${USER_NAME}
WORKDIR /opt/advshop

# Copy built JAR from builder stage
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/advshop/build/libs/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run application
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
