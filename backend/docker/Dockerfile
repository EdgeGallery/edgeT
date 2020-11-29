#
#   Copyright 2020 Huawei Technologies Co., Ltd.
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#

FROM openjdk:11.0.6-jre-slim
ARG  VTP_VERSION=1.3.2

ENV CATALINA_HOME=/opt/vtp \
    OCOMP_IP=localhost \
    OCOMP_PORT=50051

WORKDIR $CATALINA_HOME

RUN apt-get update && apt-get install -y sudo zip wget && \
    groupadd -r edgeT && useradd -m --no-log-init -r -g edgeT edgeT && \
    usermod -aG sudo edgeT && echo "edgeT ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && chmod -R 777 /usr/local/ && \
    chown -R edgeT:edgeT $CATALINA_HOME

USER edgeT

RUN wget -O TOMCAT.tar.gz "https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz" && \
    wget -O VTP.zip "https://nexus.onap.org/content/repositories/releases/org/onap/vnfsdk/refrepo/vnf-sdk-marketplace/$VTP_VERSION/vnf-sdk-marketplace-$VTP_VERSION.war" && \
    mkdir -p $CATALINA_HOME && \
    tar --strip-components=1 -xf TOMCAT.tar.gz -C $CATALINA_HOME && \
    rm -rf $CATALINA_HOME/webapps && mkdir -p $CATALINA_HOME/webapps/ROOT && \
    unzip VTP.zip -d $CATALINA_HOME/webapps/ROOT && rm -rf $CATALINA_HOME/webapps/ROOT/apidocs && \
    sed -i 's/onapapi\/vnfsdk-marketplace\///g' $CATALINA_HOME/webapps/ROOT/WEB-INF/web.xml && \
    sed -i 's/vnfsdk\.marketplace/vtp/g' $CATALINA_HOME/webapps/ROOT/WEB-INF/web.xml && \
    sed -i 's/vnfsdkmarketplace/vtp/g' $CATALINA_HOME/webapps/ROOT/WEB-INF/classes/log4j2.properties && \
    echo 'export CATALINA_OPTS="$CATALINA_OPTS -Xms64m -Xmx256m -XX:MaxPermSize=64m"' > $CATALINA_HOME/bin/setenv.sh && \
    echo 'export JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"' >> $CATALINA_HOME/bin/setenv.sh && \
    rm -rf TOMCAT.tar.gz VTP.zip

EXPOSE 8080

ENTRYPOINT $CATALINA_HOME/bin/startup.sh; tail -F $CATALINA_HOME/logs/*
