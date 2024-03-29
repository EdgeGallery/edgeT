############################################################################
# Copyright 2021 Huawei Technologies Co., Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#  http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
############################################################################

import sys
import os.path
import subprocess

csar_file_path = sys.argv[1]
result_json_path = sys.argv[2]
append_string = "{"


def does_file_exist(path_of_the_file):
    if not os.path.exists(path_of_the_file):
        print(path_of_the_file + "doesn't exist")
        sys.exit(1)


def append_json_output(testcase_name):
    global append_string
    append_string = append_string + "\n \"" + testcase_name + "\": "
    output = subprocess.getoutput("ocomp --product EdgeGallery " + testcase_name + " --zipFilePath " + csar_file_path + " --format json")
    append_string = append_string + output + ","


def main_function():
    global append_string
    does_file_exist(csar_file_path)

    test_case_list = ["compliance-check-1", "compliance-check-2", "compliance-check-3", "compliance-check-4"]
    for case in test_case_list:
        append_json_output(case)

    append_string = append_string + "}"
    subprocess.getoutput("echo \"" + append_string + "\" > " + result_json_path)


main_function()
