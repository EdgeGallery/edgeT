package org.eg.edgeT;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eg.TestCaseUtil;
import org.eg.constant.Constant;
import org.eg.constant.ExceptionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of validating .mf file must be in root directory.
 */
public class SuffixTestCase {
    static int exitCode = 0;
    public static void main(String[] args) {
        System.out.println("Reading parameters in main ");
        String zipFilePath = args[0];
        String jsonFilePath = args[1];
        SuffixTestCase suffixTestCase = new SuffixTestCase();
        exitCode = suffixTestCase.execute(zipFilePath, jsonFilePath);
        System.out.println("Exited from main of SuffixTestCase");
        System.exit(0);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SuffixTestCase.class);

    public int execute(String filePath, String josnFilePath) {
        boolean is_first_entry = true;
        boolean is_last_entry = false;
        boolean is_valid = true;
        String localString = "";
        try (ZipFile zipFile = new ZipFile(filePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                // root directory and file is end of mf
                if (entry.getName().split(Constant.SLASH).length == 2
                        && TestCaseUtil.fileSuffixValidate("mf", entry.getName())) {
                    localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.SUCCESS, Constant.EMPTY,"error_file", 0, is_first_entry, is_last_entry, is_valid);
                    is_first_entry = false;
                }
            }
        } catch (IOException e) {
            LOGGER.error("SuffixTestCase execute failed. {}", e.getMessage());
            localString = TestCaseUtil.toJsonString(localString, josnFilePath, Constant.FAILED, ExceptionConstant.INNER_EXCEPTION,"error_file", 0, is_first_entry, is_last_entry, is_valid);
            is_valid = false;
            is_first_entry = false;
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
