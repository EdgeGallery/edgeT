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
 * Implementation of validating sourcePath availability.
 */

public class SourcePathTestCase {
    static int exitCode = 0;
    public static void main(String[] args) {
        System.out.println("Reading parameters in main ");
        String zipFilePath = args[0];
        String jsonFilePath = args[1];
        SourcePathTestCase sourcePathTestCase  = new SourcePathTestCase();
        exitCode = sourcePathTestCase.execute(zipFilePath, jsonFilePath);
        System.out.println("Exited from main of SourcePathTestCase");
        System.exit(0);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SourcePathTestCase.class);

    private static Set<String> pathSet = new HashSet<String>();

    public int execute(String filePath, String josnFilePath) {
        boolean is_first_entry = true;
        boolean is_last_entry = false;
        boolean is_valid = true;
        String localString = "";

        Set<String> sourcePathSet = new HashSet<String>();
        try (ZipFile zipFile = new ZipFile(filePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();

                String path = entryName.substring(entryName.indexOf(Constant.SLASH) + 1).trim();
                pathSet.add(TestCaseUtil.removeLastSlash(path));

                // root directory and file is end of mf
                if (entry.getName().split(Constant.SLASH).length == 2
                        && TestCaseUtil.fileSuffixValidate("mf", entry.getName())) {
                    Set<String> prefix = new HashSet<String>() {
                        {
                            add("Source");
                        }
                    };
                    sourcePathSet = TestCaseUtil.getPathSet(zipFile, entry, prefix);
                }
            }
        } catch (IOException e) {
            LOGGER.error("SourcePathTestCase execute failed. {}", e.getMessage());
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.INNER_EXCEPTION,"error_file", 0, is_first_entry, is_last_entry, is_valid);
            is_valid = false;
            is_first_entry = false;
        }
        if (pathSet.containsAll(sourcePathSet)){
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.SUCCESS, Constant.EMPTY, "error_file", 0, is_first_entry, is_last_entry, is_valid);
        }
        else{
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.SOURCE_PATH_FILE_NOT_EXISTS, "error_file", 0, is_first_entry, is_last_entry, is_valid);
            is_valid = false;
        }
        is_first_entry = false;
        is_last_entry = true;
        localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.SOURCE_PATH_FILE_NOT_EXISTS, "error_file", 0,  is_first_entry, is_last_entry, is_valid);
        if(is_valid){
            return 0;
        }
        else{
            return 1;
        }
    }
}
