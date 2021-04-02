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

package org.eg.edgeT;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eg.TestCaseUtil;
import org.eg.constant.Constant;
import org.eg.constant.ExceptionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of validating TOSCA.meta file.
 */
public class TOSCAFileTestCase {
    static int exitCode = 0;
    public static void main(String[] args) {
        System.out.println("Reading parameters in main ");
        String zipFilePath = args[0];
        String jsonFilePath = args[1];
        TOSCAFileTestCase tOSCAFileTestCase  = new TOSCAFileTestCase();
        exitCode = tOSCAFileTestCase.execute(zipFilePath, jsonFilePath);
        System.out.println("Exited from main of FourthTestCase");
        System.exit(0);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TOSCAFileTestCase.class);
    private static final String TOSCA_META = "TOSCA.meta";

    private static Set<String> pathSet = new HashSet<String>();
    private static Set<String> field = new HashSet<String>() {
        {
            add("Entry-Definitions");
        }
    };

    public int execute(String filePath, String josnFilePath) {
        boolean is_first_entry = true;
        boolean is_last_entry = false;
        boolean is_valid = true;
        String localString = "";
        Set<String> sourcePathSet = new HashSet<String>();
        boolean isExistTosca = false;
        try (ZipFile zipFile = new ZipFile(filePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                String path = entryName.substring(entryName.indexOf("/") + 1).trim();

                // suit for pattern of Artifacts/test,not Artifacts/test/
                pathSet.add(TestCaseUtil.removeLastSlash(path));

                if (TOSCA_META.equals(entryName.substring(entryName.lastIndexOf("/") + 1).trim())) {
                    isExistTosca = true;
                    // some fields not exist in tosca.meta file
                    if (!TestCaseUtil.isExistAll(zipFile, entry, field)) {
                        localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.TOSCA_LOSS_FIELD, "error_file", 0, is_first_entry, is_last_entry, is_valid);
                    }
                    sourcePathSet = TestCaseUtil.getPathSet(zipFile, entry, field);
                }
            }
        } catch (IOException e) {
            LOGGER.error("TOSCAFileTestCase execute failed. {}", e.getMessage());
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.INNER_EXCEPTION, "error_file", 0, is_first_entry, is_last_entry, is_valid);
            is_valid = false;
            is_first_entry = false;
        }
        is_last_entry = true;
        if( isExistTosca == false){
            localString = TestCaseUtil.toJsonString(localString, josnFilePath,Constant.FAILED, ExceptionConstant.TOSCA_FILE_NOT_EXISTS, "error_file", 0, is_first_entry, is_last_entry, is_valid);
            is_valid = false;
        }
        else if (pathSet.containsAll(sourcePathSet)){
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.SUCCESS, Constant.EMPTY, "error_file", 0, is_first_entry, is_last_entry, is_valid);
        }
        else
        {
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.FILE_NOT_EXIT, "error_file", 0, is_first_entry, is_last_entry, is_valid);
            is_valid = false;
        }
        if(is_valid){
            return 0;
        }
        else{
            return 1;
        }
    }
}
