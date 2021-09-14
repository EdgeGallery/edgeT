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

package org.edget.manager.model;

public class ExecutionsResult {
	private int id;
	private int tester_id;
	private String execution_id;
	private int testcase_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTester_id() {
		return tester_id;
	}

	public void setTester_id(int tester_id) {
		this.tester_id = tester_id;
	}

	public String getExecution_id() {
		return execution_id;
	}

	public void setExecution_id(String execution_id) {
		this.execution_id = execution_id;
	}

	public int getTestcase_id() {
		return testcase_id;
	}

	public void setTestcase_id(int testcase_id) {
		this.testcase_id = testcase_id;
	}

}
