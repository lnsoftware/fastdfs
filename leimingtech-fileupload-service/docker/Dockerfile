# Name:     leimingtech-search
# Time:     2018-03-29

FROM java:8-jre-alpine

MAINTAINER kuangwg <kuangweiguo@leimingtech.com>

RUN mkdir /app

WORKDIR /app

COPY leimingtech-fileupload.jar /app

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/leimingtech-fileupload.jar"]

EXPOSE 8102