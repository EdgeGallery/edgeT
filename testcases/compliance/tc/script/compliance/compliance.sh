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

csar_file_path=$1
result_json_path=$2

ls $csar_file_path
if [ $? -ne 0 ]; then
  echo "provide csar file path"
  exit 1
fi

Append_String="{"

#compliance-check-1
Append_String=$Append_String"\n \"compliance-check-1\": "
output_json=$(ocomp --product EdgeGallery compliance-check-1 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json,

#compliance-check-2
Append_String=$Append_String"\n \"compliance-check-2\": "
output_json=$(ocomp --product EdgeGallery compliance-check-2 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json,

#compliance-check-3
Append_String=$Append_String"\n \"compliance-check-3\": "
output_json=$(ocomp --product EdgeGallery compliance-check-3 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json,

#compliance-check-4
Append_String=$Append_String"\n \"compliance-check-4\": "
output_json=$(ocomp --product EdgeGallery compliance-check-4 --zipFilePath $csar_file_path --format json)
Append_String=$Append_String$output_json

Append_String=$Append_String"}"

echo $Append_String > $result_json_path
