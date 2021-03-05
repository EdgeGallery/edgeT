# Copyright 2020 Huawei Technologies Co., Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#     http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import subprocess
import json
import sys


def main_function():

    final_testcase_json = []
    output = subprocess.getoutput("ocomp product-list --format json")
    product_list = json.loads(output)

    manager_ip = sys.argv[1]
    manager_port = sys.argv[2]

    for entry in product_list:
        if entry["product"] == "open-cli":
            continue
        output = subprocess.getoutput("ocomp schema-list --product " + entry["product"] + " --format json")
        schema_list = json.loads(output)
        for item in schema_list:
            case = {"scenario": item["product"], "testsuite": item["service"], "testcase": item["command"]}
            final_testcase_json.append(case)

    # update testcase table
    tester_ip = subprocess.getoutput("hostname -I")
    tester_ip = tester_ip.replace(" ", "")
    tester_port = 50051
    subprocess.getoutput("mkdir $OPEN_CLI_HOME/data/" + tester_ip + "_" + str(tester_port))
    subprocess.getoutput("mkdir $OPEN_CLI_HOME/data/artifacts/" + tester_ip + "_" + str(tester_port))
    subprocess.getoutput("mkdir $OPEN_CLI_HOME/data/tmp/" + tester_ip + "_" + str(tester_port))
    subprocess.getoutput(
        "sed -i 's?cli.data.dir.*?cli.data.dir=$s{env:OPEN_CLI_HOME}/data/" + tester_ip + "_" + str(tester_port) + "?g' /opt/ocomp/conf/open-cli.properties")

    subprocess.getoutput(
        "sed -i 's?cli.artifact.dir.*?cli.artifact.dir=$s{env:OPEN_CLI_HOME}/data/artifacts/" + tester_ip + "_" + str(tester_port) + "?g' /opt/ocomp/conf/open-cli.properties")
    subprocess.getoutput(
        "sed -i 's?cli.tmp.dir.*?cli.tmp.dir=$s{env:OPEN_CLI_HOME}/data/tmp/" + tester_ip + "_" + str(tester_port) + "?g' /opt/ocomp/conf/open-cli.properties")
    tester_list = {"ip": tester_ip, "port": str(tester_port), "test_case_list": final_testcase_json}

    cmd = "curl -v -d '" + json.dumps(tester_list) + "' -H 'Content-Type: application/json' http://" \
          + manager_ip + ":" + manager_port + "/edgeT/manager/tester"
    print(cmd)
    subprocess.getoutput(cmd)


main_function()

