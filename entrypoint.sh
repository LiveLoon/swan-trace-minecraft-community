#!/bin/bash
set -e

JAR_NAME="paper.jar"
JAR="/root/${JAR_NAME}"
DEFAULT_URL="https://fill-data.papermc.io/v1/objects/b1d8f6bfa1b6101fa8e947b53041cb3bdf5540e7b83b6547ca19ba7edefeb083/paper-26.2-129.jar"
PAPER_URL="${PAPER_URL:-$DEFAULT_URL}"

# 若挂载进来的 jar 不存在，则下载
if [ ! -s "$JAR" ]; then
    echo "[entrypoint] $JAR 不存在，开始下载..."
    echo "[entrypoint] URL: $PAPER_URL"
    curl -fL --retry 3 --retry-delay 2 -o "${JAR}.part" "$PAPER_URL"
    mv "${JAR}.part" "$JAR"
    echo "[entrypoint] 下载完成：$(ls -lh "$JAR")"
else
    echo "[entrypoint] 已存在 $JAR，跳过下载"
fi

# 启动 cron
crond

# 启动 Minecraft 服务端（前台）
exec java ${JAVA_OPTS:--Xms2G -Xmx4G} -jar "$JAR" nogui
