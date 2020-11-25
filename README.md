# edgeT

**Edge Gallery Test Platform**

# Introduction
A micro-services to manage, execute, run test cases (developed in different run-time like java, python, go, script, ROBOT, docker, etc) and with dashboard to analyze the result. It can be integrated with other edge gallery services. Helps to develop test cases once (either by community or partner or operators or app developer) use it across at various devops phases includes CICD, app development, production deployment for user-service verification , etc as One platform for all testing.

# Architecture

![输入图片说明](https://images.gitee.com/uploads/images/2020/1125/125248_4fc929ca_7639331.png "屏幕截图.png")

## OCOMP - Test executor

It facilitates test flow execution and test case execution with agility, scalability in place. Every test case is modeled into YAML along with required implementation, which can be done with different scripting language like bash script, python script or programming languages like java.

## VTP - RESTful controller

It provides the RESTful controller to manage test cases, test flow  and execute them.

# Domain Model
## Scenario

Scenario is an logical entity to model any given situations for which test cases are made, ex: compliance and verification.

## Test suite

Test suite is an logical entity helps to group the tests into hierarchy with the notation x.y.z

## Test case

Test case models the given real test case with required Inputs and Outputs. 

## Execution

Execution models every execution of given test case with unique identifier

## Profile

Profile models the System configurations and Pre-defined test case parameters. Ex: GSMA profile 


# How to setup
```
docker-compose up
```
# RESTful
```
swagger: '2.0'
info:
  version: 1.0.0
  title: ''
basePath: /v1/edegT
tags:
  - name: VTP Profile
  - name: VTP Scenario
  - name: VTP Testsuite
  - name: VTP Testcase
  - name: VTP Execution
paths:
  '/profiles':
    get:
      tags:
        - VTP Profile
      summary: List available profiles
      description: ''
      operationId: listTestProfiles
      produces:
        - application/json
      parameters: []
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestProfile'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    post:
      tags:
        - VTP Profile
      summary: Create profile.
      description: ''
      operationId: createProfile
      consumes:
        - application/json
      produces:
        - application/json
      parameters: []
      responses:
        '409':
          description: Test profile does already exist with given name
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/profiles/{profileName}':
    get:
      tags:
        - VTP Profile
      summary: ' Retrieve profile details'
      description: ''
      operationId: getTestProfile
      produces:
        - application/json
      parameters:
        - name: profileName
          in: path
          description: Test profile name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            $ref: '#/definitions/VTPTestProfile'
        '404':
          description: Test profile does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    put:
      tags:
        - VTP Profile
      summary: 'Update profile. To remove a profile parameter, set its value to null or empty.'
      description: ''
      operationId: updateProfile
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - name: profileName
          in: path
          description: Test profile name
          required: true
          type: string
      responses:
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    delete:
      tags:
        - VTP Profile
      summary: Delete profile
      description: ''
      operationId: deleteProfile
      parameters:
        - name: profileName
          in: path
          description: Test profile name
          required: true
          type: string
      responses:
        '404':
          description: Test profile does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/scenarios':
    get:
      tags:
        - VTP Scenario
      summary: ' List available test scenarios'
      description: ''
      operationId: listTestScenarios
      produces:
        - application/json
      parameters: []
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestScenario'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    post:
      tags:
        - VTP Scenario
      summary: Create scenario.
      description: 'Registare scenario in to system'
      operationId: createScenario
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - name: scenario
          in: body
          required: true
          schema: 
            $ref: '#/definitions/VTPTestScenario'
      responses:
        '409':
          description: Test scenario does already exist with given name
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError' 
  '/scenarios/{scenario}':
    get:
      tags:
        - VTP Scenario
      summary: ' Retrive given test scenarios'
      description: ''
      operationId: getTestScenario
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            $ref: '#/definitions/VTPTestScenario'
        '404':
          description: Test scenario does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    put:
      tags:
        - VTP Scenario
      summary: Update scenario.
      description: 'Update scenario'
      operationId: updateScenario
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - in: body
          name: updatedScenario
          required: true
          schema: 
            $ref: '#/definitions/VTPTestScenario'
      responses:
        '409':
          description: Test scenario does already exist with given name
          schema:
            $ref: '#/definitions/VTPError'
        '404':
          description: Test scenario does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    delete:
      tags:
        - VTP Scenario
      summary: ' Delete given test scenarios'
      description: ''
      operationId: deleteTestScenario
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
        '404':
          description: Test scenario does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/scenarios/{scenario}/testsuites':
    get:
      tags:
        - VTP Testsuite
      summary: 'List available test suite in a given scenario'
      description: 'List available test suite'
      operationId: listTestSuites
      produces:
        - application/json
      parameters: 
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestSuite'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    post:
      tags:
        - VTP Testsuite
      summary: Create Test suite.
      description: 'Registare Test suite in to system'
      operationId: createTestSuite
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: body
          required: true
          schema: 
            $ref: '#/definitions/VTPTestSuite'
      responses:
        '409':
          description: Test suite does already exist with given name
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError' 
  '/scenarios/{scenario}/testsuites/{testsuite}':
    get:
      tags:
        - VTP Testsuite
      summary: ' Retrive given test suite'
      description: ''
      operationId: getTestSuite
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: path
          description: Test suite name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            $ref: '#/definitions/VTPTestSuite'
        '404':
          description: Test suite does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    put:
      tags:
        - VTP Testsuite
      summary: Update test suite.
      description: 'Update test suite'
      operationId: updateTestsuite
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: path
          description: Test suite name
          required: true
          type: string
        - name: updatedTestSuite
          in: body
          required: true
          schema: 
            $ref: '#/definitions/VTPTestSuite'
      responses:
        '409':
          description: Test suite does already exist with given name
          schema:
            $ref: '#/definitions/VTPError'
        '404':
          description: Test suite does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    delete:
      tags:
        - VTP Testsuite
      summary: 'Delete given test suite'
      description: 'Delete given test suite'
      operationId: deleteTestSuite
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: path
          description: Test suite name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
        '404':
          description: Test suite does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/testcases':
    put:
      tags:
        - VTP Testcase
      summary: Upload, Update and upgrate Test cases.
      description: 'Upload Test cases in to system and system will discover the corresponding scenario and test suites and if they does not exist, then will create. if Test case already exist with given name, then system will update the correspoding test case binaries'
      operationId: uploadTestCase
      consumes:
        - multipart/form-data
      produces:
        - application/json
      parameters:
        - name: testcase
          in: formData
          description: Test case File
          required: true
          type: file
      responses:
        '200':
          description: Test case updated successfully
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError' 
  '/scenarios/{scenario}/testsuites/{testsuite}/testcases':
    get:
      tags:
        - VTP Testcase
      summary: 'List available test case'
      description: 'List available test cases'
      operationId: listTestCases
      produces:
        - application/json
      parameters: 
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: path
          description: Test suite name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestCase'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/scenarios/{scenario}/testsuites/{testsuite}/testcases/{testcase}':
    get:
      tags:
        - VTP Testcase
      summary: ' Retrive given test case'
      description: ''
      operationId: getTestCase
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: path
          description: Test suite name
          required: true
          type: string
        - name: testcase
          in: path
          description: Test case name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            $ref: '#/definitions/VTPTestCase'
        '404':
          description: Test case does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    delete:
      tags:
        - VTP Testcase
      summary: 'Delete given test case'
      description: 'Delete given test case'
      operationId: deleteTestCase
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testsuite
          in: path
          description: Test suite name
          required: true
          type: string
        - name: testcase
          in: path
          description: Test case name
          required: true
          type: string
      responses:
        '200':
          description: successful operation
        '404':
          description: Test case does not exist
          schema:
            $ref: '#/definitions/VTPError'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'  
  '/scenarios/{scenario}/testcases':
    get:
      tags:
        - VTP Testcase
      summary: ' List available test cases'
      description: ''
      operationId: listTestcases
      produces:
        - application/json
      parameters:
        - name: scenario
          in: path
          description: Test scenario name
          required: true
          type: string
        - name: testSuiteName
          in: query
          description: Test suite name
          required: false
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestCase'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/executions/{executionId}':
    get:
      tags:
        - VTP Execution
      summary: ' Retrieve test execution complete details'
      description: ''
      operationId: getTestExecution
      produces:
        - application/json
      parameters:
        - name: executionId
          in: path
          description: Test execution Id
          required: true
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            $ref: '#/definitions/VTPTestExecution'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
  '/executions':
    get:
      tags:
        - VTP Execution
      summary: ' List test executions'
      description: ''
      operationId: listTestExecutions
      produces:
        - application/json
      parameters:
        - name: requestId
          in: query
          description: Test request Id
          required: false
          type: string
        - name: scenario
          in: query
          description: Test scenario name
          required: false
          type: string
        - name: testsuiteName
          in: query
          description: Test suite name
          required: false
          type: string
        - name: testcaseName
          in: query
          description: Test case name
          required: false
          type: string
        - name: profileName
          in: query
          description: Test profile name
          required: false
          type: string
        - name: startTime
          in: query
          description: Test execution start time
          required: false
          type: string
        - name: endTime
          in: query
          description: Test execution end time
          required: false
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestExecution'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
    post:
      tags:
        - VTP Execution
      summary: 'Execute one or more test cases within or across scenarios and/or test suites'
      description: 'Execute the test case with given inputs in ''executions'' form-data as key-value pair of parameter''s name vs parameter''s value. If parameter is binary type thenmulti-part form-data ''file'' should be used to feed the binary file content and it can be more than once. To use the given file as input parameter, prefix the value with file://<filename>.'
      operationId: executeTestcases1
      consumes:
        - multipart/form-data
        - application/json
      produces:
        - application/json
      parameters:
        - name: requestId
          in: query
          description: Request Id
          required: false
          type: string
        - name: file
          in: formData
          description: Testcase File arguments
          required: false
          type: array
          items:
            $ref: '#/definitions/FormDataBodyPart'
          collectionFormat: multi
        - name: executions
          in: formData
          required: false
          type: string
      responses:
        '200':
          description: successful operation
          schema:
            type: array
            items:
              $ref: '#/definitions/VTPTestExecution'
        '500':
          description: Failed to perform the operation
          schema:
            $ref: '#/definitions/VTPError'
definitions:
  VTPTestProfile:
    type: object
    properties:
      name:
        type: string
      properties:
        type: array
        items:
          $ref: '#/definitions/VTPTestProfileProperty'
  VTPTestProfileProperty:
    type: object
    properties:
      scenario:
        type: string
      testSuiteName:
        type: string
      testCaseName:
        type: string
      inputParameterName:
        type: string
      value:
        type: string
  VTPTestScenario:
    type: object
    properties:
      name:
        type: string
      description:
        type: string
  VTPTestSuite:
    type: object
    properties:
      name:
        type: string
      description:
        type: string
  VTPTestCase:
    type: object
    properties:
      scenario:
        type: string
      testCaseName:
        type: string
      testSuiteName:
        type: string
      descripton:
        type: string
      author:
        type: string
      inputs:
        type: array
        items:
          $ref: '#/definitions/VTPTestCaseInput'
      outputs:
        type: array
        items:
          $ref: '#/definitions/VTPTestCaseOutput'
  VTPTestCaseInput:
    type: object
    properties:
      name:
        type: string
      description:
        type: string
      type:
        type: string
      defaultValue:
        type: string
      isOptional:
        type: boolean
      metadata:
        $ref: '#/definitions/VTPMap'
  VTPTestCaseOutput:
    type: object
    properties:
      name:
        type: string
      description:
        type: string
      type:
        type: string
  VTPTestExecution:
    type: object
    properties:
      scenario:
        type: string
      testCaseName:
        type: string
      testSuiteName:
        type: string
      executionId:
        type: string
      requestId:
        type: string
      profile:
        type: string
      parameters:
        $ref: '#/definitions/VTPMap'
      results:
        $ref: '#/definitions/VTPMap'
      status:
        type: string
      startTime:
        type: string
      endTime:
        type: string
  VTPError:
    type: object
    properties:
      code:
        type: string
      message:
        type: string
      httpStatus:
        type: integer
        format: int32
  VTPMap:
    type: object
```