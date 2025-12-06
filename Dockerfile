FROM ubuntu:22.04

# 设置非交互模式（避免 tzdata 等弹窗）
ENV DEBIAN_FRONTEND=noninteractive

# 替换为阿里云 Ubuntu 镜像源（加速下载）
RUN sed -i 's|http://[a-z0-9\.]*\.archive\.ubuntu\.com|https://mirrors.aliyun.com|g' /etc/apt/sources.list && \
    sed -i 's|http://security\.ubuntu\.com|https://mirrors.aliyun.com|g' /etc/apt/sources.list

# 安装 Python 和 git
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        python3.10 \
        python3-pip \
	    openjdk-21-jdk \
        git \
    && rm -rf /var/lib/apt/lists/*

# 创建应用目录（/home/app）
RUN mkdir -p /home/app

# 设置 pip 国内源
RUN pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple

# 设置工作目录
WORKDIR /home/app

# 克隆 musicdl 并安装（在同一个 RUN 中完成，避免目录问题）
RUN git clone https://github.com/CharlesPikachu/musicdl.git && \
    cd musicdl && \
    pip install .

# 拷贝本地 JAR 文件（确保宿主机 build/libs/ 下有该文件）
COPY build/libs/music-download-1.0.jar /home/app/
COPY read_pkl.py /home/app/
COPY search_music.py /home/app/

EXPOSE 8080
#开机启动
ENTRYPOINT ["java","-jar","/home/app/music-download-1.0.jar"]