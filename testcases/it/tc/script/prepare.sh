# Copyright 2021 Huawei Technologies Co., Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#     http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

export Var1='\${mepauth_apiRoot}\/\${mepauth_apiName}\/\${apiVersion}\/\${apipath}'
export Var2='mep\/\${apipath}'
sed -i "s/$Var1/$Var2/g" /home/api/tests/resource/EGresource/Keywords/MEPKeywords/TokenKeywords.robot

export Var1='\${apiRoot}\/\${mgmt_apiName}\/\${apiVersion}\/applications\/\${appInstanceId}\/services'
export Var2='mep\/\${mgmt_apiName}\/\${apiVersion}\/applications\/\${appInstanceId}\/services'
sed -i "s/$Var1/$Var2/g" /home/api/tests/resource/EGresource/Keywords/MEPKeywords/RegisterKeys.robot

export Var1='\${apiRoot}\/\${mgmt_apiName}\/\${apiVersion}\/applications\/\${appInstanceId}\/services'
export Var2='mep\/\${mgmt_apiName}\/\${apiVersion}\/applications\/\${appInstanceId}\/services'
sed -i "s/$Var1/$Var2/g" /home/api/tests/resource/EGresource/Keywords/MEPKeywords/GetAppServicesKeys.robot


sed -i '/^\*\*\* Settings \*\*\*.*/a Resource          ..\/..\/..\/resource\/EGresource\/Keywords\/MEPKeywords\/TokenKeywords.robot' /home/api/tests/integration/EGCases/MEP/2AppServiceRegister.robot
sed -i '/^success_register_app_service.*/a \ \ \ \ Generate_Token' /home/api/tests/integration/EGCases/MEP/2AppServiceRegister.robot

sed -i '/^\*\*\* Settings \*\*\*.*/a Resource          ..\/..\/..\/resource\/EGresource\/Keywords\/MEPKeywords\/TokenKeywords.robot' /home/api/tests/integration/EGCases/MEP/3GetAppService.robot
sed -i '/^success_app_services_all_get.*/a \ \ \ \ Generate_Token' /home/api/tests/integration/EGCases/MEP/3GetAppService.robot

sed -i '/^\ \ \ \ LoginInfo.*/i \ \ \ \ Login Portal    ${mecm}' /home/api/tests/integration/EGCases/MECM/0LoginMecm.robot

sed -i '/^\ \ \ \ Retrieves_all_application_packages_KW.*/i \ \ \ \ Onboards_an_APP_package_to_mec_host_KW' /home/api/tests/integration/EGCases/MECM/6apm.robot
sed -i '/^\ \ \ \ Returns_specific_app_package_info_KW.*/i \ \ \ \ Onboards_an_APP_package_to_mec_host_KW' /home/api/tests/integration/EGCases/MECM/6apm.robot
sed -i '/^\ \ \ \ Download_application_package_CSAR_KW.*/i \ \ \ \ Onboards_an_APP_package_to_mec_host_KW' /home/api/tests/integration/EGCases/MECM/6apm.robot

sed -i '/^\ \ \ \ Terminate_application_KW.*/i \ \ \ \ Login Portal    ${mecm}\n\ \ \ \ LoginInfo' /home/api/tests/integration/EGCases/MECM/Delete_6edgeapplication.robot

sed -i '/^\ \ \ \ Add_new_apprulemgr_record_KW.*/i \ \ \ \ Login Portal    ${mecm}\n\ \ \ \ LoginInfo' /home/api/tests/integration/EGCases/MECM/2ESR_app_rulemgr.robot


sed -i '/^\ \ \ \ Add_new_apprulemgr_record_KW.*/i \ \ \ \ Login Portal    ${mecm}\n\ \ \ \ LoginInfo' /home/api/tests/integration/EGCases/MECM/2ESR_app_rulemgr.robot


sed -i '/^\*\*\* Settings \*\*\*.*/a Resource          ..\/..\/..\/resource\/EGresource\/Keywords\/LoginPortal.robot\nLibrary           ..\/..\/..\/resource\/EGresource\/utils.py\nVariables         ..\/..\/..\/resource\/dataList1.py' /home/api/tests/integration/EGCases/MECM/2ESR_app_rulemgr.robot

