FROM ubuntu:xenial

# 环境
COPY ./docker/sources.list /etc/apt/sources.list
RUN apt-get update --fix-missing -y && apt-get update -y

# JAVA环境
RUN apt-get install -y openjdk-8-jdk apt-transport-https

# ENV
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV LANG C.UTF-8

# 项目
WORKDIR /proj
ADD ./target/convert-v1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]