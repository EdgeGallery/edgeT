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

import java.util.ArrayList;

public class TsResult {
	   private String testCaseName;
	   private String testSuiteName;
	   ArrayList<String> inputs=new ArrayList<String>();
	   ArrayList<String> outputs=new ArrayList<String>();

	   public String getTestSuiteName() {
	      return testSuiteName;
	   }

	   public void setTestSuiteName(String testSuiteName) {
	      this.testSuiteName = testSuiteName;
	   }

	   public String getTestCaseName() {
		      return testCaseName;
	   }

	   public void setTestCaseName(String testCaseName) {
	      this.testCaseName = testCaseName;
	   }

	   public ArrayList<String> getInputs() {
		      return inputs;
	   }

	   public void setInputs(ArrayList<String> inputs) {
	      this.inputs = inputs;
	   }

	   public ArrayList<String> getOutputs() {
		      return outputs;
	   }

	   public void setOutputs(ArrayList<String> outputs) {
	      this.outputs = outputs;
	   }

}
