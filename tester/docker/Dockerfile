FROM openjdk:11.0.6-jre-slim
ARG  OCOMP_VERSION=6.0.0

ENV OPEN_CLI_HOME=/opt/ocomp \
    OPEN_CLI_PRODUCT_IN_USE=open-cli

WORKDIR $OPEN_CLI_HOME
RUN apt-get update && apt-get install -y sudo zip wget && \
    groupadd -r edgeT && useradd -m --no-log-init -r -g edgeT edgeT && \
    usermod -aG sudo edgeT && echo "edgeT ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && chmod -R 777 /usr/local/ && \
    chown -R edgeT:edgeT $OPEN_CLI_HOME

USER edgeT

RUN wget -O OCOMP.zip "https://nexus.onap.org/service/local/artifact/maven/redirect?r=releases&g=org.onap.cli&a=cli-zip&e=zip&v=$OCOMP_VERSION" && \
    unzip OCOMP.zip -d $OPEN_CLI_HOME && \
    rm -rf OCOMP.zip && \
    mkdir -p $OPEN_CLI_HOME/data $OPEN_CLI_HOME/logs $OPEN_CLI_HOME/open-cli-schema $OPEN_CLI_HOME/testcase && \
    rm -rf $OPEN_CLI_HOME/script/* && \
    chmod +x $OPEN_CLI_HOME/bin/*.sh && \
    ln -sf $OPEN_CLI_HOME/bin/oclip.sh /usr/local/bin/ocomp && \
    rm $OPEN_CLI_HOME/lib/cli-products-*.jar

EXPOSE 50051

ENTRYPOINT $OPEN_CLI_HOME/bin/oclip-grpc-server.sh