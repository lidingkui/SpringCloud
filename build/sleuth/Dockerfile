FROM openjdk:8-jdk-slim
VOLUME /tmp
ADD sleuth-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'

ENV PORT=8075
EXPOSE $PORT

ENV JAVA_OPTS="-XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=20 -Xms75m -Xmx300m -Duser.timezone=UTC -Djavax.net.debug=handshake,data"

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILE -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /app.jar" ]
