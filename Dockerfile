FROM archlinux:latest

WORKDIR /root

# 国内镜像加速
RUN echo -e "Server = https://mirrors.ustc.edu.cn/archlinux/\$repo/os/\$arch\nServer = https://mirrors.tuna.tsinghua.edu.cn/archlinux/\$repo/os/\$arch\nServer = https://mirror.rackspace.com/archlinux/\$repo/os/\$arch" > /etc/pacman.d/mirrorlist

# 依赖：JDK + cron + 压缩 + curl（用于下载 jar）
RUN pacman -Syu --noconfirm jdk-openjdk cronie zip openssh
RUN pacman -Scc --noconfirm

# 时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ENV TZ=Asia/Shanghai

# 备份目录
RUN mkdir -p /root/backups

# 备份脚本：每周一保留一份带日期的快照，其余时间覆盖 world_latest
RUN cat > /usr/local/bin/backup.sh << 'EOF'
#!/bin/bash
tar -I zstd -cf /root/backups/world_latest.tar.zst /root/world
if [ $(date +%u) -eq 1 ]; then
    cp /root/backups/world_latest.tar.zst /root/backups/world_$(date +%Y%m%d).tar.zst
fi
EOF
RUN chmod +x /usr/local/bin/backup.sh
RUN echo "0 8 * * * /usr/local/bin/backup.sh" | crontab -

# 入口脚本：jar 不存在时自动下载，存在则直接启动
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

EXPOSE 22 25565 27865 24454 19132 34832 3000

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
