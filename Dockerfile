FROM eclipse-temurin:17

EXPOSE 8080
ARG PINPOINT_VERSION
ARG AGENT_ID
ARG APP_NAME
ENV JAVA_OPTS="-javaagent:/pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar -Dpinpoint.agentId=${AGENT_ID} -Dpinpoint.applicationName=${APP_NAME} -Dspring.profiles.active=${SPRING_PROFILES}"

RUN mkdir -p build
COPY build/libs/sleact-0.0.1-SNAPSHOT.jar build/app.jar
WORKDIR build

CMD java -jar ${JAVA_OPTS} app.jar
