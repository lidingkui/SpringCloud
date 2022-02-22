#!/usr/bin/env bash
$1
cd ..
mvn compile
cd build/


cd eureka/
./eureka_deploy.sh $1
cd ..

cd gateway/
./gateway_deploy.sh $1
cd ..

cd converter/
./converter_deploy.sh $1
cd ..

cd oicp/
./oicp_deploy.sh $1
cd ..


cd ocpi/
./ocpi_deploy.sh $1
cd ..

cd sleuth/
./sleuth_deploy.sh $1
cd ..

echo !!!!!!  END  !!!!!!
