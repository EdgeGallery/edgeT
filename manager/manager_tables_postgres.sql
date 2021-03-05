/*
* Copyright 2020 Huawei Technologies Co., Ltd.
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*     http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

CREATE DATABASE "edgeT";
\c edgeT;

DROP TABLE IF EXISTS TESTER;

CREATE TABLE TESTER (
	ID                   serial	       NOT NULL,
	IP                   inet       NULL,
	PORT		         int       NULL,
	created_at           timestamptz       default current_timestamp,
	CONSTRAINT tester_table_pkey PRIMARY KEY (ID),
	UNIQUE (IP,PORT)
);

DROP TABLE IF EXISTS TESTCASE;

CREATE TABLE TESTCASE (
	ID                   serial       NOT NULL,
	TESTER_ID            int       references TESTER(ID),
	SCENARIO             VARCHAR(200)       NULL,
	TESTSUITE            VARCHAR(200)       NULL,
	TESTCASE		     VARCHAR(200)       NULL,
	created_at           timestamptz       default current_timestamp,
	CONSTRAINT testcase_table_pkey PRIMARY KEY (ID),
	UNIQUE (TESTER_ID,SCENARIO,TESTSUITE,TESTCASE)
);

DROP TABLE IF EXISTS EXECUTION;

CREATE TABLE EXECUTION (
	ID                   serial       NOT NULL,
	TESTER_ID            int       references TESTER(ID),
	TESTCASE_ID            int       references TESTCASE(ID),
	EXECUTION_ID            VARCHAR(200)       NOT NULL,
	created_at           timestamptz       default current_timestamp,
	CONSTRAINT execution_table_pkey PRIMARY KEY (ID)
);

