#!/bin/bash

export FILE=$(dirname "$0")/docker/Dockerfile

docker build -t edgegallery/edget-be --build-arg=VTP_VERSION=1.6.1 -f "$FILE" .
