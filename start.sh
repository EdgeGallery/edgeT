# Copyright 2020 Huawei Technologies Co., Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#     http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

if [[ $MODE != "aio" && $MODE != "dist" ]]; then
  echo "Set MODE"
  echo "valid values are: aio, dist"
  exit
fi

if [ -z $TEST_NAME ]; then
  echo "Set TEST_NAME"
  exit
fi

if [[ $MODE == "aio" && $TEST_NAME == *","* ]]; then
  echo "Only single tester is supported in aio mode"
  exit
fi

network_cmd=" "
port_cmd="-p 8080:8080"
if [[ $MODE == "aio" ]]; then
  network_cmd=" --network=host "
  port_cmd=""
fi

TEST_NAME=`echo $TEST_NAME | sed -e "s/,/ /g"`

docker volume create results
docker volume create workspace

namager_details=""
if [[ $MODE == "dist" ]]; then
  docker run -d --name edget-db --env POSTGRES_USER=postgres --env POSTGRES_PASSWORD=123 edgegallery/edget-postgres:latest
  docker run -d --name edget-manager --link edget-db --restart always edgegallery/edget-manager:latest
  MANAGER_IP=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' edget-manager`
  MANAGER_PORT=9090
  namager_details="--env MANAGER_PORT=$MANAGER_PORT --env MANAGER_IP=$MANAGER_IP"
fi

for testsuite in $TEST_NAME;
  do
    docker run -d --name edget-$testsuite-tester --env MODE=$MODE $namager_details --mount source=results,target=/opt/ocomp/data --mount source=workspace,target=/tmp/workspace $network_cmd edgegallery/edget-$testsuite-testcase:latest
  done
docker run -d --name edget-controller --env MODE=$MODE $namager_details $port_cmd --mount source=results,target=/opt/vtp/data,readonly --mount source=workspace,target=/tmp/workspace $network_cmd edgegallery/edget-be:latest
