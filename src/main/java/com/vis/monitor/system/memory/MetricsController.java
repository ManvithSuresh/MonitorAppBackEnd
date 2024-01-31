package com.vis.monitor.system.memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/system")
public class MetricsController {

    @Autowired
    private MetricsService mService;

    @GetMapping("/cpu")
    public List<Cpu> getCpuUsage() {
    	log.info("..........................................GET METHOD CPU................................................");
        log.info("Received request for CPU usage.");
        List<Cpu> last60SecondsUsageOfCPU = mService.getLast60SecondsUsageOfCPU();
        log.info("Returning CPU metrics for the last 60 seconds.");
        return last60SecondsUsageOfCPU;
    }

    @GetMapping("/ram")
    public List<Ram> getRamUsage() {
    	log.info("..........................................GET METHOD RAM................................................");
        log.info("Received request for RAM usage.");
        List<Ram> last60SecondsUsageOfRam = mService.getLast60SecondsUsageOfRam();
        log.info("Returning RAM metrics for the last 60 seconds.");
        return last60SecondsUsageOfRam;
    }

    @GetMapping("/disk")
    public List<Disk> getLastUsageOfDisk() {
    	log.info("..........................................GET METHOD DISK................................................");
        log.info("Received request for disk usage.");
        List<Disk> lastUsageOfDisk = mService.getLastUsageOfDisk();
        log.info("Returning disk metrics.");
        return lastUsageOfDisk;
    }
}
