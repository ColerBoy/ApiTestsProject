FROM jenkins/jenkins:lts-jdk17

USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends wget apt-transport-https software-properties-common gnupg ca-certificates \
    && wget -q https://packages.microsoft.com/config/debian/12/packages-microsoft-prod.deb \
    && dpkg -i packages-microsoft-prod.deb \
    && rm packages-microsoft-prod.deb \
    && apt-get update \
    && apt-get install -y --no-install-recommends maven powershell \
    && rm -rf /var/lib/apt/lists/*

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

COPY casc/ /usr/share/jenkins/ref/casc_configs/
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/jenkins.yaml

USER jenkins
