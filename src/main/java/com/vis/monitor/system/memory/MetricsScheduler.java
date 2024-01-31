package com.vis.monitor.system.memory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vis.monitor.system.memory.utils.CpuUtils;
import com.vis.monitor.system.memory.utils.DiskUtils;
import com.vis.monitor.system.memory.utils.RamUtils;
import com.vis.monitor.utils.AppConstants;

import lombok.extern.log4j.Log4j2;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Component
@Log4j2
public class MetricsScheduler {
	
	
	   @Scheduled(fixedDelay = 60000)
	   public void checkMetrics() {
		log.info("....................SCHEDULED TASK TO MONITOR SYSTEM METRICS  (RAM , CPU , DISK) STARTED....................");
		log.info("checkDiskSpace() method is invoked");
		checkDiskSpace();
		log.info("checkCpuUsage() method is invoked");
		checkCpuUsage();
		log.info("checkRamUsage() method is invoked");
        checkRamUsage();
        log.info("Scheduled task to monitor system metrics (RAM ,CPU , DISK) completed.");
		log.info("....................SCHEDULED TASK TO MONITOR SYSTEM METRICS  (RAM , CPU , DISK) ENDED....................");
       
       
	}

	
	private void checkDiskSpace() {
        double currentDiskSpaceUsage = DiskUtils.checkDiskSpace(); ;
        if (currentDiskSpaceUsage > AppConstants.DISK_THRESHOLD) {
        	log.info("The Disk usage is under normal condition"+currentDiskSpaceUsage);
        }else {
            log.warn("Disk space usage exceeds the threshold! Current usage: {}%", currentDiskSpaceUsage);
        }
    }

//	   
//	private void checkCpuUsage() {
//		   SystemInfo systemInfo = new SystemInfo();
//		    CentralProcessor processor = systemInfo.getHardware().getProcessor();
//
//	    double currentCpuUsage = CpuUtils.checkCpuUsage(processor);
//
//	    if (currentCpuUsage > AppConstants.CPU_THRESHOLD) {
//	        log.info("The CPU usage is under normal condition. Current usage: {}%", currentCpuUsage);
//	    } else {
//	        log.warn("CPU usage exceeds the threshold! Current usage: {}%", currentCpuUsage);
//	    }
//	}

	private void checkCpuUsage() {
	
	    double currentCpuUsage = CpuUtils.checkCpuUsage();

	    if (currentCpuUsage > AppConstants.CPU_THRESHOLD) {
	        log.info("The CPU usage is under normal condition. Current usage: {}%", currentCpuUsage);
	    } else {
	        log.warn("CPU usage exceeds the threshold! Current usage: {}%", currentCpuUsage);
	    }
	}

 
    private void checkRamUsage() {
        double currentRamUsage = RamUtils.checkRamUsage();
        if (currentRamUsage < AppConstants.RAM_THRESHOLD) {
        	log.info("The Ram usage is under normal condition " + currentRamUsage);
        }else {
            log.warn("RAM usage exceeds the threshold! Current usage: {}%", currentRamUsage);
        }
    }
}
