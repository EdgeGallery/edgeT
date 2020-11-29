#!/bin/bash

export BASE_DIR=$(dirname "$0")
export FILE=$BASE_DIR/docker/Dockerfile
docker build -t edgegallery/edget-feature2-testcase -f "$FILE" .
