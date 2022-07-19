FROM openjdk:8u332-slim-buster
MAINTAINER tanglx
VOLUME /tmp
ADD target/wechat-*.jar wechat.jar
RUN bash -c 'touch /wechat.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/wechat.jar"]
CMD [""]