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

from edgegallery/edget-tester:latest

#Keep the test case YAML under tc/open-cli-schema
#Keep the test case impl tc/script and refer these script as $OPEN_CLI_HOME/script/<script-name>
#   This script will read inputs mentioned in YAML parameters and execute the test case urandom
#   produce results mentioned in YAML results->attributes. And these attributes are filled using
#   jsonpath, so script should produce the json output
#   Refer for detailed example at https://github.com/onap/cli/blob/master/products/onap-dublin
#   /features/integration/src/main/resources/open-cli-schema/vnf-tosca-lcm-dublin-schema.yaml

ADD ./tc $OPEN_CLI_HOME

RUN sudo chmod -R +x $OPEN_CLI_HOME/script
