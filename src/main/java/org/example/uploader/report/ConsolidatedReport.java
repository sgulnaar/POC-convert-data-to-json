package org.example.uploader.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.uploader.service.impl.UserProjectServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class ConsolidatedReport {
    private static Logger log = LogManager.getLogger(UserProjectServiceImpl.class);
    private static final List<String> JSONS = new ArrayList<>();

    public static void addJson(LinkedHashMap<ReportKey, String> reportDataMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportDataMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error while converting reportDataMap to json", e);
        }
        JSONS.add(json);
    }

    public static void addJson(ReportData reportData) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error while converting reportData to json", e);
        }
        JSONS.add(json);
    }

    public static void buildReport() {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path reportPath = path.resolve("ConsolidatedReport.json");
        Resource resource = null;
        try {
            resource = new UrlResource(reportPath.toUri());
        } catch (MalformedURLException e) {
            log.error("could not check if ConsolidatedReport.json exists or not", e);
        }
        if (Objects.isNull(resource) || !resource.exists()) {
            try {
                Files.createFile(reportPath);
            } catch (IOException e) {
                log.error("error while creating ConsolidatedReport.json file", e);
            }
        }
        log.info(JSONS);
        try {
            Files.write(reportPath, JSONS.toString().getBytes());
        } catch (IOException e) {
            log.error("error while writing jsons to ConsolidatedReport.json file", e);
            throw new RuntimeException("error while writing jsons to ConsolidatedReport.json file", e);
        }
        JSONS.clear(); // clearing json for next report
    }

    public static void main(String[] args) {
        /* Approach 1 testing */
        LinkedHashMap<ReportKey, String> reportDataMap =
                new LinkedHashMap<>();
        reportDataMap.put(ReportKey.FLAG, "a");
        reportDataMap.put(ReportKey.SOURCE_TABLE, "b");
        reportDataMap.put(ReportKey.TARGET_TABLE, "c");
        reportDataMap.put(ReportKey.RESULT, "d");
        reportDataMap.put(ReportKey.TIME_TAKEN, "e");
        addJson(reportDataMap);
        addJson(reportDataMap);
        buildReport();

        /* Approach 2 testing */
//        ReportData reportData1 = new ReportData();
//        reportData1.setFlag("a");
//        reportData1.setSourceTable("b");
//        reportData1.setTargetTable("c");
//        reportData1.setResult("d");
//        reportData1.setTimeTaken("e");
//        addJson(reportData1);
//        ReportData reportData2 = new ReportData();
//        reportData2.setFlag("aa");
//        reportData2.setSourceTable("ba");
//        reportData2.setTargetTable("ca");
//        reportData2.setResult("da");
//        reportData2.setTimeTaken("ea");
//        addJson(reportData2);
//        buildReport();
    }
}
