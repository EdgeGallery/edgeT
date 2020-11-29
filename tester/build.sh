#!/bin/bash

export FILE=$(dirname "$0")/docker/Dockerfile

docker build --build-arg OCOMP_VERSION=6.0.0 -t edgegallery/edget-tester -f "$FILE" .
