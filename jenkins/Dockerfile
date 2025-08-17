FROM jenkins/jenkins:lts-jdk17

# Preinstall plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# JCasC + Job DSL
COPY casc/ /usr/share/jenkins/ref/casc/
COPY jobs/ /usr/share/jenkins/ref/jobs-dsl/

# Autoload JCasC
ENV CASC_JENKINS_CONFIG=/usr/share/jenkins/ref/casc/jenkins.yaml \
    JENKINS_OPTS="--argumentsRealm.passwd.admin=admin --argumentsRealm.roles.user=admin --argumentsRealm.roles.admin=admin"

# Optionally, drop a marker to auto-run a seed DSL on first start via init.groovy.d
RUN mkdir -p /usr/share/jenkins/ref/init.groovy.d
COPY jobs/seed.groovy /usr/share/jenkins/ref/init.groovy.d/seed.groovy