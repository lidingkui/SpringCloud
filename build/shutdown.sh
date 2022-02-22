#!/usr/bin/env bash
docker-compose down
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/oicp:local-dev
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/converter:local-dev
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/sleuth:local-dev
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/ocpi:local-dev
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/eureka:local-dev
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/gateway:local-dev