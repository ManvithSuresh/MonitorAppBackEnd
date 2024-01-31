package com.vis.monitor.system.memory.utils;

import com.vis.monitor.system.memory.Disk;
import com.vis.monitor.system.memory.Metrics;
import com.vis.monitor.system.memory.MetricsService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

@Log4j2
public class DiskUtils {

    public static double checkDiskSpace() {
        double totalUsedSpace = 0.0;

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        for (Path rootPath : rootDirectories) {
            try {
                FileStore fileStore = Files.getFileStore(rootPath);
                Metrics metricsInfo = new Metrics();
                Disk disk = createDisk(rootPath);

                metricsInfo.setDiskUsage(disk);

                // Log disk information
                log.info("Drive: " + disk.getDriveLetter());
                log.info("Total space: " + disk.getTotalSpace() + " bytes");
                log.info("Free space: " + disk.getFreeSpace() + " bytes");
                log.info("Used space (calculated): " + disk.getUsedSpace() + " bytes");
                log.info("Metrics added to service: " + metricsInfo);

                // Optional: Calculate the used space by visiting each file
                long usedSpace = calculateUsedSpace(rootPath);
                log.info("Used space (calculated): " + usedSpace + " bytes");

                totalUsedSpace += usedSpace;

                // Add metrics to the service
                MetricsService.addMetrics(metricsInfo);

                // Log metrics added to the service
                log.info("Metrics added to service: " + metricsInfo);

                log.info("-------------------------------");
            } catch (IOException e) {
                log.error("Error while checking disk space", e);
            }
        }

        return totalUsedSpace;
    }

    private static Disk createDisk(Path rootPath) {
        String driveLetter = rootPath.toString();
        double totalSpace = 0.0;
        double freeSpace = 0.0;
        double usedSpace = 0.0;

        try {
            FileStore fileStore = Files.getFileStore(rootPath);
            totalSpace = fileStore.getTotalSpace();
            freeSpace = fileStore.getUsableSpace();
            usedSpace = totalSpace - freeSpace;
        } catch (IOException e) {
            log.error("Error while creating disk information", e);
        }

        Disk diskInfo = new Disk();
        diskInfo.setDriveLetter(driveLetter);
        diskInfo.setFreeSpace(freeSpace);
        diskInfo.setTotalSpace(totalSpace);
        diskInfo.setUsedSpace(usedSpace);

        log.info("Disk information: " + diskInfo);
        return diskInfo;
    }

    private static long calculateUsedSpace(Path rootPath) {
        final long[] usedSpace = {0};

        try {
            Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    usedSpace[0] += attrs.size();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // Handle file visit failure if needed
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            log.error("Error while calculating used space", e);
        }

        return usedSpace[0];
    }
}
