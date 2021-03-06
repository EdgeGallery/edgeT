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


docker stop edget-controller
docker rm -f edget-controller
#cd manager/deployment/install/ ; docker-compose down; cd -
docker stop edget-db edget-manager
docker rm -f edget-db edget-manager
tester_docker_indexes=`docker ps --filter name=edget* -aq`

if [[ $tester_docker_indexes != "" ]];then
  echo $tester_docker_indexes | xargs docker stop | xargs docker rm
fi
docker volume rm results workspace
