```
swagger: '2.0'
info:
  version: 1.0.0
  title: ''
basePath: /v1/vtp
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
