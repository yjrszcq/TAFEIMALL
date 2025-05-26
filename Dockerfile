# 使用多阶段构建减少镜像体积
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

RUN apt-get update && \
    apt-get install -y git && \
    git clone https://github.com/yjrszcq/TAFEIMALL.git /app && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN cp src/main/resources/application-docker.yaml src/main/resources/application.yaml

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 运行时镜像
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080

# 设置时区（如果需要）
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

ENTRYPOINT ["java", "-jar", "app.jar"]