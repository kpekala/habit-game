FROM amazoncorretto:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN apk add --update --no-cache python3 && ln -sf python3 /usr/bin/python
RUN python3 -m ensurepip
RUN pip3 install --no-cache --upgrade pip setuptools
RUN pip3 install mysql
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY db/start_app.py start_app.py
ENTRYPOINT [ "python","start_app.py" ]
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=production","/app.jar"]