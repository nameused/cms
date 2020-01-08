FROM openjdk:8-jdk-alpine
VOLUME /tmp
# 将项目打包后的jar文件作为统配符*.jar 添加到镜像中
# 项目每次打包版本时不用更改此配置文件
ADD *.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]