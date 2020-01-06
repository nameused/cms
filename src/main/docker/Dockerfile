FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD cms-0.0.1.jar cms-0.1.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cms-0.1.jar"]