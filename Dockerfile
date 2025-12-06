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

# 设置工作目录
WORKDIR /home/app

RUN CD /home/app

RUN git clone https://kkgithub.com/CharlesPikachu/musicdl
RUN cd musicdl
RUN pip install . -i https://pypi.tuna.tsinghua.edu.cn/simple
RUN ..
#将本地项目jar包拷贝到Docker容器中的位置
ADD build/libs/music-download-1.0.jar /home/app

EXPOSE 8080
#开机启动
ENTRYPOINT ["java","-jar","/music-download-1.0.jar"]