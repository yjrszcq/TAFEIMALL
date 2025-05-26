# 使用多阶段构建减少镜像体积
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

# 使用Alpine的apk安装git并克隆项目
RUN apk update && \
    apk add --no-cache git && \
    git clone https://github.com/yjrszcq/TAFEIMALL.git /app && \
    rm -rf /var/cache/apk/*

WORKDIR /app

# 应用docker配置文件
RUN cp src/main/resources/application-docker.yaml src/main/resources/application.yaml

# 复制pom.xml并下载依赖
COPY pom.xml .
RUN mvn dependency:go-offline

# 复制源代码并构建项目
COPY src ./src
RUN mvn clean package -DskipTests

# 运行时镜像
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080

# 设置时区
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app.jar"]