FROM registry.access.redhat.com/openjdk/openjdk-11-rhel7:1.15-3.1681911696

USER root

ADD  ./target/*.jar /deployments/

EXPOSE 8080 

USER 185