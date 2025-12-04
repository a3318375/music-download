FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/library/openjdk:21-slim

# 设置非交互式安装（避免 tzdata 提示）
ENV DEBIAN_FRONTEND=noninteractive

# 安装 Python 3.10、pip、git 及其他必要工具
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        python3.10 \
        python3.10-venv \
        python3-pip \
        git \
        curl \
        && \
    # 创建软链接，使 python3 和 pip3 可用
    ln -sf /usr/bin/python3.10 /usr/bin/python3 && \
    ln -sf /usr/bin/pip3 /usr/bin/pip && \
    # 升级 pip
    #pip install --upgrade pip && \
    # 克隆并安装 musicdl
    git clone https://github.com/CharlesPikachu/musicdl.git /tmp/musicdl && \
    cd /tmp/musicdl && \
    pip install . && \
    # 清理缓存和临时文件以减小镜像体积
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/musicdl

#将本地项目jar包拷贝到Docker容器中的位置
ADD build/libs/music-download-1.0.jar ./

EXPOSE 8080
#开机启动
ENTRYPOINT ["java","-jar","/music-download-1.0.jar"]