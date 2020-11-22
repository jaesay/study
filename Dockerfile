FROM openjdk:11.0.8-jre-slim
WORKDIR application
ARG JAR_FILE=build/libs/demo*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
