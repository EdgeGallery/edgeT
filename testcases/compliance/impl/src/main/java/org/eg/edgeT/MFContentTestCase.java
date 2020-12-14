package org.eg.edgeT;

import java.io.*;
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
 * Implementation of validating .mf file content.
 */
public class MFContentTestCase {
    static int exitCode = 0;
    public static void main(String[] args) {
        System.out.println("Reading parameters in main ");
        String zipFilePath = args[0];
        String jsonFilePath = args[1];

        File f = new File(zipFilePath);
        if (f.exists()) {
            System.out.println(zipFilePath + "Exists");
            MFContentTestCase mFContentTestCase = new MFContentTestCase();
            exitCode = mFContentTestCase.newexecute(zipFilePath, jsonFilePath);
        }
        else {
            System.out.println(zipFilePath + "Does not Exists");
        }
        System.out.println("Exited from main");
        System.exit(0);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MFContentTestCase.class);

    private static Set<String> field = new HashSet<String>() {
        {
            add("app_product_name");
            add("app_provider_id");
            add("app_package_version");
            add("app_release_data_time");
            add("app_package_description");
        }
    };

    public int newexecute(String filePath, String josnFilePath) {
        boolean is_first_entry = true;
        boolean is_last_entry = false;
        boolean is_valid = true;
        String localString = "";
        try (ZipFile zipFile = new ZipFile(filePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().split(Constant.SLASH).length == 2
                        && TestCaseUtil.fileSuffixValidate("mf", entry.getName())) {
                    // some fields not exist in tosca.meta file
                    if (TestCaseUtil.isExistAll(zipFile, entry, field)) {
                        localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.SUCCESS, Constant.EMPTY, "error_file", 0, is_first_entry, is_last_entry, is_valid);
                    } else {
                        localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.MF_LOSS_FIELD, "error_file", 0, is_first_entry, is_last_entry, is_valid);
                        is_valid = false;
                    }
                }
                is_first_entry = false;
            }
        } catch (IOException e) {
            LOGGER.error("TOSCAFileTestCase execute failed. {}", e.getMessage());
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.INNER_EXCEPTION,"error_file", 0,  is_first_entry, is_last_entry, is_valid);
            is_valid = false;
        }
        is_last_entry = true;
        localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.FILE_NOT_EXIST,"error_file", 0, is_first_entry, is_last_entry, is_valid);
        if(is_valid){
            return 0;
        }
        else{
            return 1;
        }
    }
}
