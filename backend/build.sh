#!/bin/bash

export FILE=$(dirname "$0")/docker/Dockerfile

docker build -t edgegallery/edget-be -f "$FILE" .
