#!/bin/bash
#
#   Copyright 2020 Huawei Technologies Co., Ltd.
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#

#edgegallery/edget-tester
cd ./tester
chmod +x ./build.sh
./build.sh
cd ..

#Build back end: edgegallery/edget-be
cd ./backend
chmod +x ./build.sh
./build.sh
cd ..

#Build Test case:#edgegallery/edget-feature1-testcase <- edget-tester
cd ./testcases/feature1
chmod +x ./build.sh
./build.sh
cd ../..
