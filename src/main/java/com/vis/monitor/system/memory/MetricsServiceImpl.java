package com.vis.monitor.system.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MetricsServiceImpl implements MetricsService {

    @Override
    public List<Cpu> getLast60SecondsUsageOfCPU() {
    	log.info(".....................CPU IMPL START....................");
        log.info("Getting last 60 seconds CPU usage...");
        List<Cpu> cpuUsage = new ArrayList<>();

        Iterator<Metrics> iterator = queueMetricsInfo.iterator();
        while (iterator.hasNext()) {
            cpuUsage.add(iterator.next().getCpuUsage());
        }

        log.info("Last 60 seconds CPU usage retrieved: {}", cpuUsage);
        return cpuUsage;
    }

    @Override
    public List<Ram> getLast60SecondsUsageOfRam() {
    	log.info(".....................RAM IMPL START....................");
        log.info("Getting last 60 seconds RAM usage...");
        List<Ram> ramUsage = new ArrayList<>();

        Iterator<Metrics> iterator = queueMetricsInfo.iterator();
        while (iterator.hasNext()) {
            ramUsage.add(iterator.next().getRamUsage());
        }

        log.info("Last 60 seconds RAM usage retrieved: {}", ramUsage);
        return ramUsage;
    }
    
    


    @Override
    public List<Disk> getLastUsageOfDisk() {
        log.info(".....................DISK IMPL START....................");
        		
        log.info("Getting all disk metrics...");
        List<Disk> allDiskMetrics = new ArrayList<>();

        log.info("Size of queueMetricsInfo: {}", queueMetricsInfo.size());

        for (Metrics metrics : queueMetricsInfo) {
            Disk diskMetrics = metrics.getDiskUsage();
            log.info("Disk Metrics in Queue: {}", diskMetrics);

            // Add the diskMetrics to the list
            allDiskMetrics.add(diskMetrics);
        }

        log.info("Size of allDiskMetrics before returning: {}", allDiskMetrics.size());

        if (!allDiskMetrics.isEmpty()) {
            log.info("Returning all disk metrics.");
            return allDiskMetrics;
        } else {
            log.warn("No metrics available for getLastUsageOfDisk");
            return Collections.emptyList();
            
        }
    }



    }	
   






