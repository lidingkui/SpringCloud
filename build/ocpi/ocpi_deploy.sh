docker login --username=17521188586 registry.cn-qingdao.aliyuncs.com --password womawan123

#cd ../../msg/
#mvn install
#cd ../build/ocpi/

cd ../../ocpi/
mvn package
cd ../build/ocpi/
cp ../../ocpi/target/ocpi-1.0-SNAPSHOT.jar .


docker build -t ocpi:$1 --build-arg SPRING_PROFILE=$1 .

docker tag ocpi:$1 registry.cn-qingdao.aliyuncs.com/ocpi-adapter/ocpi:$1
docker push registry.cn-qingdao.aliyuncs.com/ocpi-adapter/ocpi:$1

docker rmi ocpi:$1
docker rmi registry.cn-qingdao.aliyuncs.com/ocpi-adapter/ocpi:$1

