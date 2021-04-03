# Copyright 2021 Huawei Technologies Co., Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#     http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#MEP testcase requests
curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f09' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MEP_1GenerateToken",
  "testCaseName": "success_generate_token",
  "parameters": {
  "envFilePath": "file://env.sh"
  }
}]'

curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f08' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MEP_2AppServiceRegister",
  "testCaseName": "success_register_app_service",
  "parameters": {
  "envFilePath": "file://env.sh"
  }
}]'

curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f07' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MEP_3GetAppService",
  "testCaseName": "success_app_services_all_get",
  "parameters": {
  "envFilePath": "file://env.sh"
  }
}]'

#MECM testcase requests
curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f06' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@mecm_env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MECM_0LoginMecm",
  "testCaseName": "get_login_info",
  "parameters": {
  "envFilePath": "file://mecm_env.sh"
  }
}]'

curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f06' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@mecm_env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MECM_0LoginMecm",
  "testCaseName": "loginmecm",
  "parameters": {
  "envFilePath": "file://mecm_env.sh"
  }
}]'

#MEP testsuite requests
curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f07' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MEP",
  "testCaseName": "mep",
  "parameters": {
  "envFilePath": "file://env.sh"
  }
}]'

#MECM testsuite requests
curl -v --location --request POST 'http://localhost:8080/v1/vtp/executions?requestId=ebaa5f21-ed68-4098-97a9-775ac8800f07' --header 'Content-Type: multipart/form-data' --header 'Content-Type: multipart/form-data' --form 'file=@mecm_env.sh' --form 'executions=[{
  "scenario": "EdgeGallery",
  "testSuiteName": "MECM",
  "testCaseName": "mecm",
  "parameters": {
  "envFilePath": "file://mecm_env.sh"
  }
}]'
