Execution
==========
```
curl --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f09' --header 'Content-Type: multipart/form-data' --form 'executions=[{
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
}]'

[
  {
    "scenario": "edgeT",
    "testCaseName": "compliance-check-1",
    "testSuiteName": "compliance",
    "executionId": "ebaa5f21-ed68-4098-97a9-775ac8800f09-1606663315050",
    "parameters": {
      "name": "demo"
    },
    "results": {
      "output1": "Given name demo"
    },
    "status": "COMPLETED",
    "startTime": "2020-11-29T15:21:55.031",
    "endTime": "2020-11-29T15:21:55.068"
  },
  {
    "scenario": "edgeT",
    "testCaseName": "compliance-check-2",
    "testSuiteName": "compliance",
    "executionId": "ebaa5f21-ed68-4098-97a9-775ac8800f09-1606663315088",
    "parameters": {
      "name": "demo1"
    },
    "results": {
      "output1": "Given name demo1"
    },
    "status": "COMPLETED",
    "startTime": "2020-11-29T15:21:55.068",
    "endTime": "2020-11-29T15:21:55.107"
  }
]
```

Scenarios
==========

```
curl http://localhost:8080/v1/vtp/scenarios
[
  {
    "name": "edgeT",
    "description": ""
  }
]


Test Suites
===========
curl http://localhost:8080/v1/vtp/scenarios/edgeT/testsuites
[
  {
    "name": "compliance",
    "description": ""
  }
]

```
Test cases
==========

```
curl http://localhost:8080/v1/vtp/scenarios/edgeT/testcases
[
  {
    "testCaseName": "compliance-check-1",
    "testSuiteName": "compliance",
    "inputs": [],
    "outputs": []
  },
  {
    "testCaseName": "compliance-check-2",
    "testSuiteName": "compliance",
    "inputs": [],
    "outputs": []
  }
]
```

Test Case
==========

```
curl http://localhost:8080/v1/vtp/scenarios/edgeT/testsuites/compliance/testcases/compliance-check-1
{
  "testCaseName": "compliance-check-1",
  "testSuiteName": "compliance",
  "description": "Sample test case. Add all required input under parameters and outputs under results->attributes",
  "author": "Kanag kanagaraj.manickam@huawei.com",
  "inputs": [
    {
      "name": "name",
      "description": "Name to print",
      "type": "string",
      "defaultValue": "test",
      "isOptional": true
    },
    {
      "name": "timeout",
      "description": "timeout for command to complete the given task in milliseconds",
      "type": "string",
      "defaultValue": "60000",
      "isOptional": true
    }
  ],
  "outputs": [
    {
      "name": "output1",
      "description": "sample output 1",
      "type": "string"
    }
  ]
}
```
