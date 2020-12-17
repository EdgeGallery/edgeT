#!/bin/bash


#export BASE_DIR=$(dirname "$0")
#export FILE=$BASE_DIR/docker/Dockerfile
#docker build --no-cache -t edgegallery/edget-compliance-testcase -f "$FILE" .

PWD=$(pwd)
cd $PWD/impl

mvn clean install
mvn assembly:assembly -DdescriptorId=jar-with-dependencies

cp target/impl-1.0-SNAPSHOT-jar-with-dependencies.jar ../tc/script/compliance/lib/

cd ../
export BASE_DIR=$(dirname "$0")
export FILE=$BASE_DIR/docker/Dockerfile
docker build --no-cache -t edgegallery/edget-compliance-testcase -f "$FILE" .
