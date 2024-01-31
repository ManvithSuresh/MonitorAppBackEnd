package com.vis.monitor.reports;
//
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Controller
//@RequestMapping("/api")
//public class LogController {
//
//    private static final String LOGS_DIRECTORY = "logs";
//
//    @GetMapping("/logs/{logLevel}")
//    @ResponseBody
//    public ResponseEntity<String> getLogs(@PathVariable String logLevel) {
//        String logFileName = logLevel.toLowerCase() + ".log";
//        File logFile = new File(LOGS_DIRECTORY, logFileName);
//
//        if (!logFile.exists()) {
//            // Handle the case when the requested log file does not exist
//            return ResponseEntity.notFound().build();
//        }
//
//        try {
//            String content = new String(Files.readAllBytes(logFile.toPath()));
//            return ResponseEntity.ok(content);
//        } catch (IOException e) {
//            // Handle the exception
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error reading log file");
//        }
//    }
//
//    @GetMapping("/logs/all")
//    @ResponseBody
//    public ResponseEntity<String> getAllLogs() {
//        File logsDirectory = new File(LOGS_DIRECTORY);
//        File[] logFiles = logsDirectory.listFiles();
//
//        if (logFiles == null || logFiles.length == 0) {
//            // Handle the case when there are no log files
//            return ResponseEntity.notFound().build();
//        }
//
//        StringBuilder allLogsContent = new StringBuilder();
//
//        for (File logFile : logFiles) {
//            try {
//                String content = new String(Files.readAllBytes(logFile.toPath()));
//                allLogsContent.append("=====").append(logFile.getName()).append("=====\n");
//                allLogsContent.append(content).append("\n\n");
//            } catch (IOException e) {
//                // Handle the exception
//                e.printStackTrace();
//                return ResponseEntity.status(500).body("Error reading log files");
//            }
//        }
//
//        return ResponseEntity.ok(allLogsContent.toString());
//    }
//    
//    
//    
//    @GetMapping("/api/logs/byDateTimeAndLevel")
//    public ResponseEntity<?> getLogsByDateTimeAndLevel(
//            @RequestParam("logLevel") String logLevel,
//            @RequestParam("startDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,
//            @RequestParam("endDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime) {
//
//        String logFileName = logLevel.toLowerCase() + ".log";
//        Path logFilePath = Paths.get(LOGS_DIRECTORY, logFileName);
//
//        if (!Files.exists(logFilePath) || !Files.isRegularFile(logFilePath)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        try {
//            List<String> logLines = Files.readAllLines(logFilePath);
//            String processedLogs = processLogsByDateTimeRange(logLines, startDateTime, endDateTime);
//            return ResponseEntity.ok(processedLogs);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error reading log file");
//        }
//    }
//
//    private String processLogsByDateTimeRange(List<String> logLines, LocalDateTime startDateTime, LocalDateTime endDateTime) {
//        StringBuilder processedLogs = new StringBuilder();
//
//        DateTimeFormatter logLineFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        for (String logLine : logLines) {
//            String timestampString = logLine.substring(0, 19);
//            LocalDateTime logTimestamp = LocalDateTime.parse(timestampString, logLineFormatter);
//
//            if (!logTimestamp.isBefore(startDateTime) && logTimestamp.isBefore(endDateTime)) {
//                processedLogs.append(logLine).append("\n");
//            }
//        }
//
//        return processedLogs.toString();
//    }
//}
//   

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/logs/{level}")
    public ResponseEntity<List<Logs>> getLogsByLevel(
            @RequestParam(value = "level") String level
    ) {
        List<Logs> logs = logService.getLogsByLevel(level);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/byDateTimeAndLevel")
    public ResponseEntity<List<Logs>> getLogsByLevelAndTime(
            @RequestParam(value = "level") String level,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "50") int pageSize
    ) {
        List<Logs> logs = logService.getLogsByLevelAndTime(level, startTime, endTime, page, pageSize);
        return ResponseEntity.ok(logs);
    }
}