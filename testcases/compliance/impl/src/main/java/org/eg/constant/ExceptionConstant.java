/*
* Copyright 2021 Huawei Technologies Co., Ltd.
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

package org.eg.constant;

public interface ExceptionConstant {

    String MF_LOSS_FIELD =
            ".mf file may lost the following fileds:app_product_name,app_provider_id,app_package_version,app_release_date_time or app_package_description.";
    String FILE_NOT_EXIST = ".mf file may not exist or it do not in the root directory.";
    String SOURCE_PATH_FILE_NOT_EXISTS = "some source path file in .mf may not exist.";
    String TOSCA_FILE_NOT_EXISTS = "tosca.meta not exists.";
    String TOSCA_LOSS_FIELD = "tosca.meta file may lost the following filed:Entry-Definitions.";
    String FILE_NOT_EXIT = "the value of field Entry-Definitions do not exist corresponding file";
    String INNER_EXCEPTION = "inner exception, please check the log.";
}
