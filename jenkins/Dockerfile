ARG JENKINS_TAG=lts
FROM jenkins/jenkins:${JENKINS_TAG}

USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends git ca-certificates \
    && rm -rf /var/lib/apt/lists/*
USER jenkins

# Preinstall plugins
COPY jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# JCasC + Job DSL
COPY jenkins/casc/ /usr/share/jenkins/ref/casc/
COPY jenkins/jobs/ /usr/share/jenkins/ref/jobs-dsl/
COPY jenkins/init/load-jobdsl.groovy /usr/share/jenkins/ref/init.groovy.d/load-jobdsl.groovy

# Autoload JCasC
ENV CASC_JENKINS_CONFIG=/usr/share/jenkins/ref/casc/jenkins.yml \
    JENKINS_OPTS="--argumentsRealm.passwd.admin=admin --argumentsRealm.roles.user=admin --argumentsRealm.roles.admin=admin"