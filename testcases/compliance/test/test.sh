curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f09' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@enterprise2DC.csar' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "compliance",
  "testCaseName": "compliance-check-5",
  "parameters": {
  "file": "file://enterprise2DC.csar"
  }
}]'