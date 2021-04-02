#!/bin/bash

############################################################################
# Copyright 2021 Huawei Technologies Co., Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#  http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
############################################################################

#export BASE_DIR=$(dirname "$0")
#export FILE=$BASE_DIR/docker/Dockerfile
#docker build --no-cache -t edgegallery/edget-compliance-testcase -f "$FILE" .

PWD=$(pwd)
cd $PWD/impl

mvn clean install
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
mkdir -p ../tc/script/compliance/lib

cp target/impl-1.0-SNAPSHOT-jar-with-dependencies.jar ../tc/script/compliance/lib/

cd ../
export BASE_DIR=$(dirname "$0")
export FILE=$BASE_DIR/docker/Dockerfile
docker build --no-cache -t edgegallery/edget-compliance-testcase -f "$FILE" .
