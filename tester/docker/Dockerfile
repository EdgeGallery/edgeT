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


FROM openjdk:11.0.6-jre-slim
ARG  OCOMP_VERSION=3.0.0

ENV OPEN_CLI_HOME=/opt/ocomp \
    OPEN_CLI_PRODUCT_IN_USE=open-cli

WORKDIR $OPEN_CLI_HOME
RUN apt-get update && apt-get install -y sudo zip wget && \
    groupadd -r edgeT && useradd -m --no-log-init -r -g edgeT edgeT && \
    usermod -aG sudo edgeT && echo "edgeT ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && chmod -R 777 /usr/local/ && \
    chown -R edgeT:edgeT $OPEN_CLI_HOME

USER edgeT

RUN wget -O OCOMP.zip "https://nexus.onap.org/content/repositories/releases/org/onap/cli/cli-zip/$OCOMP_VERSION/cli-zip-$OCOMP_VERSION.zip" && \
    unzip OCOMP.zip -d $OPEN_CLI_HOME && \
    rm -rf OCOMP.zip && \
    mkdir -p $OPEN_CLI_HOME/data $OPEN_CLI_HOME/logs $OPEN_CLI_HOME/open-cli-schema $OPEN_CLI_HOME/testcase && \
    rm -rf $OPEN_CLI_HOME/script/* && \
    chmod +x $OPEN_CLI_HOME/bin/*.sh && \
    ln -sf $OPEN_CLI_HOME/bin/oclip.sh /usr/local/bin/ocomp && \
    rm $OPEN_CLI_HOME/lib/cli-products-*.jar

EXPOSE 50051

ENTRYPOINT $OPEN_CLI_HOME/bin/oclip-grpc-server.sh
