#!/bin/bash

export FILE=$(dirname "$0")/docker/Dockerfile

docker build --build-arg OCOMP_VERSION=5.0.3 -t edgegallery/edget-tester -f "$FILE" .
