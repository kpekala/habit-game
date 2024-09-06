FROM amazoncorretto:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN apk add --no-cache python3 py3-pip pipx pkg-config
RUN pipx install mysql
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY db/start_app.py start_app.py
ENTRYPOINT [ "python","start_app.py" ]
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=production","/app.jar"]