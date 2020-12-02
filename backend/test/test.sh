#!/bin/bash

curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f09' --header 'Content-Type: multipart/form-data' --form 'executions=[{
  "scenario": "edgeT",
  "testSuiteName": "compliance",
  "testCaseName": "compliance-check-1",
  "parameters": {
"name": "demo"
  }
},{
  "scenario": "edgeT",
  "testSuiteName": "compliance",
  "testCaseName": "compliance-check-2",
  "parameters": {
"name": "demo1"
  }
}]' | jq

curl -v http://localhost:8080/v1/vtp/scenarios | jq
curl -v http://localhost:8080/v1/vtp/scenarios/edgeT/testsuites | jq
curl -v http://localhost:8080/v1/vtp/scenarios/edgeT/testcases | jq
curl -v http://localhost:8080/v1/vtp/scenarios/edgeT/testsuites/compliance/testcases/compliance-check-1 | jq

