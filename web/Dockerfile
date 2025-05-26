FROM node:slim AS builder

RUN apt-get update && \
    apt-get install -y git && \
    git clone https://github.com/rouwanyuanyuanyuan/myshoppingmall.git /app && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN mkdir -p Config && \
    echo "http:" > Config/application.yaml && \
    echo '  baseURL: "${HTTP_BASE_URL}"' >> Config/application.yaml && \
    echo '  timeout: ${HTTP_TIMEOUT}' >> Config/application.yaml

# 安装项目依赖
RUN npm install --no-audit --no-fund --cache /tmp/npm-cache && npm cache clean --force

# 构建 Vue 应用
RUN npm run build

# --- Production Stage ---
FROM nginx:alpine-slim

COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]