# edgeT

**Edge Gallery Test Platform**

# Introduction
A micro-services to manage, execute, run test cases (developed in different run-time like java, python, go, script, ROBOT, docker, etc) and with dashboard to analyze the result. It can be integrated with other edge gallery services. Helps to develop test cases once (either by community or partner or operators or app developer) use it across at various devops phases includes CICD, app development, production deployment for user-service verification , etc as One platform for all testing.

# Architecture

![edgeT](./docs/arch.PNG)

## Test executor

It facilitates test flow execution and test case execution with agility, scalability in place. Every test case is modeled into YAML along with required implementation, which can be done with different scripting language like bash script, python script or programming languages like java.

## RESTful controller

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


# How to build
```
./build-all.sh
```

# How to install
```
export TEST_NAME=feature2
./start.sh

# to test
./backend/test/test.sh
```

# How to uninstall
```
./stop.sh
```

# Docs

* [swagger api](./backend/docs/swagger.md)

* [Test case development guide](./testcases/docs/testcase-dev-guide.md)

* [Test case deployment guide](./testcases/docs/testcase-dep-guide.md)

* [Sample REST API](./backend/docs/api-req-res-sample.md )
