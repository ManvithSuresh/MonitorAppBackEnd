////	package com.vis.monitor.system.memory.utils;
////	
////	import lombok.extern.log4j.Log4j2;
////	
////	import java.lang.management.ManagementFactory;
////
////import org.hibernate.annotations.Comment;
////import org.springframework.scheduling.annotation.Scheduled;
////import org.springframework.stereotype.Component;
////
////import com.sun.management.OperatingSystemMXBean;
////import com.vis.monitor.system.memory.Cpu;
////import com.vis.monitor.system.memory.Metrics;
////import com.vis.monitor.system.memory.MetricsService;
////
////
////	
////	
////	
////	@Log4j2
////	public class CpuUtils {
////	
//////		public static void main(String[] args) {
//////			checkCpuUtils();
//////		}
////		
////		
////		public  static double checkCpuUsage() {
////			// TODO Auto-generated method stub
////			Cpu cpuInfo = new Cpu();
////			 OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
////	
////		        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
////		            com.sun.management.OperatingSystemMXBean sunOsBean =
////		                    (com.sun.management.OperatingSystemMXBean) osBean;
////		            
////		            double cpuUsage = sunOsBean.getSystemCpuLoad();
////		            double totalCpuUsage=cpuUsage*100;
////		            cpuInfo.setCpuUsage(totalCpuUsage);
////		           
////		            log.info("The CPU usage is: " + totalCpuUsage);
////		        
////		            Metrics metricsInfo = new Metrics();
////		            metricsInfo.setCpuUsage(cpuInfo);
////		            MetricsService.addMetrics(metricsInfo);
////    
////		         return totalCpuUsage;
////		        } else {
////		            log.warn("CPU usage monitoring is not supported on this platfom");
////			
////		}
////		        return 0.0;
////		}
////	}
//
package com.vis.monitor.system.memory.utils;

import lombok.extern.log4j.Log4j2;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import com.vis.monitor.system.memory.Cpu;
import com.vis.monitor.system.memory.Metrics;
import com.vis.monitor.system.memory.MetricsService;

@Log4j2
public class CpuUtils {

    private static final int SAMPLE_INTERVAL_MS = 1000; // Adjust this based on your desired sampling interval
    private static final int SAMPLE_COUNT = 5; // Adjust this based on the number of samples you want to collect

    public static void main(String[] args) {
        collectCpuUsageSamples();
    }

    public static void collectCpuUsageSamples() {
        double totalCpuUsage = 0.0;
        double maxCpuUsage = Double.MIN_VALUE;
        double minCpuUsage = Double.MAX_VALUE;

        for (int i = 0; i < SAMPLE_COUNT; i++) {
            double cpuUsage = checkCpuUsage();

            // Update total, max, and min CPU usage
            totalCpuUsage += cpuUsage;
            maxCpuUsage = Math.max(maxCpuUsage, cpuUsage);
            minCpuUsage = Math.min(minCpuUsage, cpuUsage);

            // Sleep for the specified interval before collecting the next sample
            try {
                Thread.sleep(SAMPLE_INTERVAL_MS);
            } catch (InterruptedException e) {
                log.error("Error while sleeping between CPU usage samples", e);
            }
        }

        // Calculate average CPU usage
        double averageCpuUsage = totalCpuUsage / SAMPLE_COUNT;

        log.info("Average CPU Usage: " + averageCpuUsage);
        log.info("Max CPU Usage: " + maxCpuUsage);
        log.info("Min CPU Usage: " + minCpuUsage);
    }

    public static double checkCpuUsage() {
        Cpu cpuInfo = new Cpu();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean =
                    (com.sun.management.OperatingSystemMXBean) osBean;

            double cpuUsage = sunOsBean.getSystemCpuLoad();
            double totalCpuUsage = cpuUsage * 100.0;
            cpuInfo.setCpuUsage(totalCpuUsage);

            log.info("The CPU usage is: " + totalCpuUsage);

            Metrics metricsInfo = new Metrics();
            metricsInfo.setCpuUsage(cpuInfo);
            MetricsService.addMetrics(metricsInfo);

            return totalCpuUsage;
        } else {
            log.warn("CPU usage monitoring is not supported on this platform");
        }
        return 0.0;
    }
}

//package com.vis.monitor.system.memory.utils;
//
//import com.vis.monitor.system.memory.Cpu;
//import com.vis.monitor.system.memory.Metrics;
//import com.vis.monitor.system.memory.MetricsService;
//
//import lombok.extern.log4j.Log4j2;
//import oshi.SystemInfo;
//import oshi.hardware.CentralProcessor;
//import oshi.util.Util;
//
//@Log4j2
//public class CpuUtils {
//
//    private static final int SAMPLE_INTERVAL_MS = 2000;
//    private static final int SAMPLE_COUNT = 5;
//
//    public static void main(String[] args) {
//        collectCpuUsageSamples();
//    }
//
//    public static void collectCpuUsageSamples() {
//        double totalCpuUsage = 0.0;
//        double maxCpuUsage = Double.MIN_VALUE;
//        double minCpuUsage = Double.MAX_VALUE;
//
//        SystemInfo systemInfo = new SystemInfo();
//        CentralProcessor processor = systemInfo.getHardware().getProcessor();
//
//        for (int i = 0; i < SAMPLE_COUNT; i++) {
//            double cpuUsage = checkCpuUsage(processor);
//
//            // Update total, max, and min CPU usage
//            totalCpuUsage += cpuUsage;
//            maxCpuUsage = Math.max(maxCpuUsage, cpuUsage);
//            minCpuUsage = Math.min(minCpuUsage, cpuUsage);
//
//            // Sleep for the specified interval before collecting the next sample
//            Util.sleep(SAMPLE_INTERVAL_MS);
//        }
//
//        // Calculate average CPU usage
//        double averageCpuUsage = totalCpuUsage / SAMPLE_COUNT;
//
//        log.info("Average CPU Usage: " + averageCpuUsage);
//        log.info("Max CPU Usage: " + maxCpuUsage);
//        log.info("Min CPU Usage: " + minCpuUsage);
//    }
//
//    public static double checkCpuUsage(CentralProcessor processor) {
//        Cpu cpuInfo = new Cpu();
//        double cpuUsage = processor.getSystemCpuLoad(0) * 100;
//        double totalCpuUsage = cpuUsage;
//      cpuInfo.setCpuUsage(totalCpuUsage);
//
//        log.info("The CPU usage is: " + cpuUsage);
//        Metrics metricsInfo = new Metrics();
//      metricsInfo.setCpuUsage(cpuInfo);
//      MetricsService.addMetrics(metricsInfo);
//        return cpuUsage;
//    }
//}
