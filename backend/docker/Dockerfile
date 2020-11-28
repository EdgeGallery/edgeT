FROM openjdk:11.0.6-jre-slim
ARG  VTP_VERSION=LATEST

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
    wget -O VTP.zip "https://nexus.onap.org/service/local/artifact/maven/redirect?r=releases&g=org.onap.vnfsdk.refrepo&a=vnf-sdk-marketplace&e=war&v=$VTP_VERSION" && \
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

RUN echo 'sed -i "s|vtp.grpc.server = localhost|vtp.grpc.server = ${OCOMP_IP}|g" $CATALINA_HOME/webapps/ROOT/WEB-INF/classes/vtp.properties' > $CATALINA_HOME/bin/start_vtp.sh

RUN echo 'sed -i "s|vtp.grpc.port = 50051|vtp.grpc.server = ${OCOMP_PORT}|g" $CATALINA_HOME/webapps/ROOT/WEB-INF/classes/vtp.properties' >> $CATALINA_HOME/bin/start_vtp.sh

RUN echo '$CATALINA_HOME/bin/startup.sh; tail -F $CATALINA_HOME/logs/*' >> $CATALINA_HOME/bin/start_vtp.sh
RUN sudo chmod +x $CATALINA_HOME/bin/start_vtp.sh

EXPOSE 8080

ENTRYPOINT $CATALINA_HOME/bin/start_vtp.sh
