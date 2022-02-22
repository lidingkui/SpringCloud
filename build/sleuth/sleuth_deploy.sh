docker login --username=17521188586 registry.cn-qingdao.aliyuncs.com --password womawan123
cd ../../sleuth/
mvn package
cd ../build/sleuth/
cp ../../sleuth/target/sleuth-1.0-SNAPSHOT.jar .

docker build -t sleuth:$1 --build-arg SPRING_PROFILE=$1 .

docker tag sleuth:$1 registry.cn-qingdao.aliyuncs.com/ocpi-adapter/sleuth:$1
docker push registry.cn-qingdao.aliyuncs.com/ocpi-adapter/sleuth:$1

docker rmi sleuth:$1
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/sleuth:$1

