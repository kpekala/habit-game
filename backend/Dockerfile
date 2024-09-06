FROM amazoncorretto:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN apk update
RUN apk add --no-cache python3 py3-pip pipx pkgconfig
RUN export MYSQLCLIENT_CFLAGS=`pkg-config mysqlclient --cflags`
RUN export MYSQLCLIENT_LDFLAGS=`pkg-config mysqlclient --libs`
RUN pip install mysqlclient --break-system-packages
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY db/start_app.py start_app.py
ENTRYPOINT [ "python","start_app.py" ]
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=production","/app.jar"]