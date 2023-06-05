#!/bin/bash

# quay.apps.mgmt.telcostack.br.telefonica.com/redhat

podman build -t $1/proxy-rest:$2 .
podman push $1/proxy-rest:$2 