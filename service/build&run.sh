# !/bin/bash
SERVICENAME='warehouse-service'
CONTAINERID=`docker ps -a | grep ${SERVICENAME} |awk '{print $1}'`

if [ -n "${CONTAINERID}" ];then
 docker stop ${CONTAINERID}
 docker rm ${CONTAINERID}
fi 

IMAGEID=`docker images | grep ${SERVICENAME} | awk '{print $3}'`

if [ -n "${IMAGEID}" ];then
 docker rmi -f ${IMAGEID}
fi

DATE=`date +"%Y%m%d%H%M"`
docker build -t ${SERVICENAME}:${DATE} .
docker run -d -p 8088:8088  -v /home/warehouse:/warehouse-service/uploadPath \
-v /home/warehouse/logs:/warehouse-service/logs \
--name warehouse-service --restart=always registry.cn-hangzhou.aliyuncs.com/junyang/warehouse-service:1001