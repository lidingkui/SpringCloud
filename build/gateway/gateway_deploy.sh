docker login --username=17521188586 registry.cn-qingdao.aliyuncs.com --password womawan123
cd ../../gateway/
mvn package
cd ../build/gateway/
cp ../../gateway/target/gateway-1.0-SNAPSHOT.jar .

docker build -t gateway:$1 --build-arg SPRING_PROFILE=$1 .

docker tag gateway:$1 registry.cn-qingdao.aliyuncs.com/ocpi-adapter/gateway:$1
docker push registry.cn-qingdao.aliyuncs.com/ocpi-adapter/gateway:$1

docker rmi gateway:$1
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/gateway:$1

