FROM archlinux:latest

WORKDIR /root

RUN echo -e "Server = https://mirrors.ustc.edu.cn/archlinux/\$repo/os/\$arch\nServer = https://mirrors.tuna.tsinghua.edu.cn/archlinux/\$repo/os/\$arch\nServer = https://mirror.rackspace.com/archlinux/\$repo/os/\$arch" > /etc/pacman.d/mirrorlist

RUN pacman -Syu --noconfirm jdk26-openjdk cronie zip tzdata openssh zstd
RUN pacman -Scc --noconfirm


# 2. 设置时区（以北京/上海为例）
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 3. 设置环境变量 TZ，让 Java 和 cron 子进程直接读取
ENV TZ=Asia/Shanghai

RUN mkdir -p /root/backups

RUN cat > /usr/local/bin/backup.sh << 'EOF'
#!/bin/bash
# 先压缩最新备份（覆盖 world_latest.tar.zst）
tar -I zstd -cf /root/backups/world_latest.tar.zst /root/world
# 判断今天是否是周一（%u 返回 1-7，1 代表周一）
if [ $(date +%u) -eq 1 ]; then
    # 复制为带日期的归档，保留每周一的一份快照
    cp /root/backups/world_latest.tar.zst /root/backups/world_$(date +%Y%m%d).tar.zst
fi
EOF

RUN chmod +x /usr/local/bin/backup.sh

RUN echo "0 8 * * * /usr/local/bin/backup.sh" | crontab -

EXPOSE 25265
EXPOSE 24454
EXPOSE 19132
EXPOSE 34832
EXPOSE 3000
EXPOSE 22

CMD ["sh", "-c", "crond && exec java -jar paper-26.2-40.jar"]

