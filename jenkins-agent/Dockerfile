# jenkins-agent/Dockerfile
ARG BASE_TAG=latest-jdk21  
FROM jenkins/inbound-agent:${BASE_TAG}

USER root

# SO tools
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl unzip ca-certificates python3 python3-pip python3-venv groff less \
    && rm -rf /var/lib/apt/lists/*

# Terraform
ARG TF_VERSION=1.9.5
RUN curl -Ls https://releases.hashicorp.com/terraform/${TF_VERSION}/terraform_${TF_VERSION}_linux_amd64.zip -o /tmp/tf.zip \
    && unzip /tmp/tf.zip -d /usr/local/bin && chmod +x /usr/local/bin/terraform \
    && rm -f /tmp/tf.zip

# AWS CLI v2
ARG AWSCLI_VERSION=2.17.40
RUN curl -Ls "https://awscli.amazonaws.com/awscli-exe-linux-x86_64-${AWSCLI_VERSION}.zip" -o /tmp/awscliv2.zip \
    && unzip /tmp/awscliv2.zip -d /tmp \
    && /tmp/aws/install && rm -rf /tmp/aws /tmp/awscliv2.zip

# Ansible
ARG ANSIBLE_CORE_VERSION=2.17.*
RUN python3 -m venv /opt/venv \
    && /opt/venv/bin/pip install --upgrade pip \
    && /opt/venv/bin/pip install --no-cache-dir "ansible-core==${ANSIBLE_CORE_VERSION}" boto3 botocore

# Collections necesarias 
COPY ../ansible/requirements.yml /tmp/requirements.yml
RUN /opt/venv/bin/ansible-galaxy collection install -p /usr/share/ansible/collections -r /tmp/requirements.yml \
    && rm -f /tmp/requirements.yml

# Conveniencia + permisos
RUN mkdir -p /work /home/jenkins/.terraform.d/plugin-cache \
    && chown -R jenkins:jenkins /work /home/jenkins/.terraform.d

ENV PATH="/opt/venv/bin:${PATH}" \
    TF_PLUGIN_CACHE_DIR=/home/jenkins/.terraform.d/plugin-cache \
    AWS_PAGER=

USER jenkins
WORKDIR /home/jenkins/agent
