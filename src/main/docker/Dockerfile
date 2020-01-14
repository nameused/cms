FROM openjdk:8-jdk-alpine
#时区设置，因alpine镜像中并没有时区相关的包，需要下载安装才行。并设置localtime和timezone为东八区
ENV TZ=Asia/Shanghai
RUN echo "http://mirrors.aliyun.com/alpine/v3.4/main/" > /etc/apk/repositories \
    && apk --no-cache add tzdata zeromq \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo '$TZ' > /etc/timezone
# 将项目打包后的jar文件作为统配符*.jar 添加到镜像中
# 项目每次打包版本时不用更改此配置文件
ADD *.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]