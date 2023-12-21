FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 小猿
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk update && apk --no-cache --purge -U upgrade
RUN apk add --no-cache --purge -uU ttf-dejavu fontconfig && rm -rf /var/cache/apk/*
RUN mkdir /usr/share/fonts/new/
ADD ./simsun.ttf /usr/share/fonts/new/
RUN fc-cache
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /warehouse-service

WORKDIR /warehouse-service

EXPOSE 8088

ADD ./warehouse-admin/target/warehouse-admin.jar ./

CMD sleep 30;java -Xms1024m -Xmx1024m -Xmn128m -Djava.security.egd=file:/dev/.urandom -jar warehouse-admin.jar

