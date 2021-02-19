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


FROM openjdk:11-jre-slim

ENV JAVA_VERSION_MAJOR=11 \
    JAVA_VERSION_MINOR=08 \
    JAVA_HOME=/usr/lib/jvm/default-jvm \
    PATH=${PATH}:/usr/lib/jvm/default-jvm/bin/ \
    MANAGER_HOST=127.0.0.1 \
    MANAGER_PORT=50051

# Install required libraries
RUN apt-get update && apt-get install -y \
    curl \
    nano \
    sudo \
    unzip \
    wget \
    odbc-postgresql \
 && rm -rf /var/lib/apt/lists/* \
 && sudo mkdir -p /usr/lib/jvm/default-jvm \
 && sudo ln -s /usr/local/openjdk-11/* /usr/lib/jvm/default-jvm

# Download and set up Tomcat
WORKDIR /opt/tomcat
RUN wget -q https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz && \
    tar xvfz apache*.tar.gz && \
    mv apache-tomcat-8.5.30/* /opt/tomcat/. && \
    sed -i 's/8080/9090/g' conf/server.xml

COPY edgeT-manager.war /opt/tomcat/
RUN rm -rf /opt/tomcat/webapps && mkdir -p /opt/tomcat/webapps/ROOT && \
    unzip /opt/tomcat/edgeT-manager.war -d /opt/tomcat/webapps/ROOT && rm -rf /opt/tomcat/edgeT-manager.war

EXPOSE 9090

CMD ["/opt/tomcat/bin/catalina.sh", "jpda", "run"]
