docker login --username=17521188586 registry.cn-qingdao.aliyuncs.com --password womawan123
cd ../../eureka/
mvn package
cd ../build/eureka/
cd ../build/eureka/
cp ../../eureka/target/eureka-1.0-SNAPSHOT.jar .

docker build -t eureka:$1 --build-arg SPRING_PROFILE=$1 .

docker tag eureka:$1 registry.cn-qingdao.aliyuncs.com/ocpi-adapter/eureka:$1
docker push registry.cn-qingdao.aliyuncs.com/ocpi-adapter/eureka:$1

docker rmi eureka:$1
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/eureka:$1

