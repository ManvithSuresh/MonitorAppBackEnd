package com.vis.monitor.reports;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class LogServiceImpl implements LogService {

    private static final Logger WARN_LOGGER = LoggerFactory.getLogger("warn");
    private static final Logger INFO_LOGGER = LoggerFactory.getLogger("info");
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("error");

    @Override
    public List<Logs> getLogsByLevel(String level) {
        return getLogs(level, null, null, 0, 0);
    }

    @Override
    public List<Logs> getLogsByLevelAndTime(String level, String startTime, String endTime, int page, int pageSize) {
        log.info("Received request with level: {}, startTime: {}, endTime: {}, page: {}, pageSize: {}", level, startTime, endTime, page, pageSize);

        List<Logs> logs = getLogs(level, startTime, endTime, page, pageSize);

        log.info("Filtered logs: {}", logs);

        return logs;
    }

    private List<Logs> getLogs(String level, String startTime, String endTime, int page, int pageSize) {
        // Convert startTime and endTime strings to Date objects
        Date startDateTime = parseDateString(startTime);
        Date endDateTime = parseDateString(endTime);

        // Choose the appropriate logger based on the requested log level
        Logger logger = getLoggerByLevel(level);

        // Get the log entries from the chosen logger
        List<String> logStrings = getLogStrings(logger);

        // Apply filtering based on criteria
        Stream<Logs> filteredLogs = logStrings.parallelStream()
                .map(this::parseLogEntry)
                .filter(logEntry -> isValidLogEntry(logEntry, startDateTime, endDateTime));

        // Apply pagination
        if (page > 0 && pageSize > 0) {
            int startIdx = (page - 1) * pageSize;
            int endIdx = startIdx + pageSize;
            filteredLogs = filteredLogs.skip(startIdx).limit(endIdx - startIdx);
        }

        return filteredLogs.collect(Collectors.toList());
    }

    private boolean isValidLogEntry(Logs logEntry, Date startDateTime, Date endDateTime) {
        return logEntry != null
                && (startDateTime == null || logEntry.getTimeStamp().after(startDateTime))
                && (endDateTime == null || logEntry.getTimeStamp().before(endDateTime));
    }
    
    private List<String> getLogStrings(Logger logger) {
        Path logFilePath = Paths.get("Logs/all_logs.log");

        try (Stream<String> lines = Files.lines(logFilePath)) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error reading log file: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private Logs parseLogEntry(String logString) {
        try {
            String[] parts = logString.split("\\s", 2);
            if (parts.length == 2) {
                String timestampStr = parts[0].trim();
                String logEntry = parts[1].trim();
                Date timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestampStr);
                String level = logEntry.substring(logEntry.indexOf('[') + 1, logEntry.indexOf(']')).trim();
                String message = logEntry.substring(logEntry.indexOf(']') + 1).trim();
                return new Logs(timestamp, level, message);
            } else {
                log.error("Invalid log entry format: {}", logString);
            }
        } catch (ParseException | StringIndexOutOfBoundsException e) {
            log.error("Error parsing log entry: {}", e.getMessage());
        }
        return null;
    }

    private Date parseDateString(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateString.replace("T", " ")); // Replace 'T' with a space
        } catch (ParseException e) {
            log.error("Error parsing date string: {}", dateString);
            return null;
        }
    }

    private Logger getLoggerByLevel(String level) {
        switch (level.toLowerCase()) {
            case "warn":
                return WARN_LOGGER;
            case "info":
                return INFO_LOGGER;
            case "error":
                return ERROR_LOGGER;
            default:
                return INFO_LOGGER;
        }
    }
}
