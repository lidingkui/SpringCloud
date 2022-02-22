docker login --username=17521188586 registry.cn-qingdao.aliyuncs.com --password womawan123

cd ../../oicp/
mvn package
cd ../build/oicp/
cp ../../oicp/target/oicp-1.0-SNAPSHOT.jar .

docker build -t oicp:$1 --build-arg SPRING_PROFILE=$1 .

docker tag oicp:$1 registry.cn-qingdao.aliyuncs.com/ocpi-adapter/oicp:$1
docker push registry.cn-qingdao.aliyuncs.com/ocpi-adapter/oicp:$1

docker rmi oicp:$1
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/oicp:$1

