docker login --username=17521188586 registry.cn-qingdao.aliyuncs.com --password womawan123

cd ../../converter/
mvn package
cd ../build/converter/
cp ../../converter/target/converter-1.0-SNAPSHOT.jar .

docker build -t converter:$1 --build-arg SPRING_PROFILE=$1 .

docker tag converter:$1 registry.cn-qingdao.aliyuncs.com/ocpi-adapter/converter:$1
docker push registry.cn-qingdao.aliyuncs.com/ocpi-adapter/converter:$1

docker rmi converter:$1
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/converter:$1

