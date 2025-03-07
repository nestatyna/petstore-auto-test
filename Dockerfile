FROM openjdk:17

WORKDIR /app

COPY . .

#RUN ./gradlew clean build --no-daemon
RUN ./gradlew clean build -x test --no-daemon

CMD ["./gradlew", "test"]
