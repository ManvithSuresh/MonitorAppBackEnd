package com.vis.monitor.server.group.monitor.status;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value =" /api")
public class MonitorServerGroupStatusController {
	@Autowired
    private MonitorServerGroupStatusService msService;

    @PostMapping("/add-monitor-server-group-status")
    public ResponseEntity<MonitorServerGroupStatus> addMonitorStatus(@RequestBody MonitorServerGroupStatus monitorStatus) {
    	MonitorServerGroupStatus savedMonitorStatus = msService.addMonitorStatus(monitorStatus); 
        
        return new ResponseEntity<>(savedMonitorStatus, HttpStatus.CREATED);
    }
    
    @PutMapping("/update-monitor-server-group-status")
    public ResponseEntity<MonitorServerGroupStatus> updateMonitorStatus(@RequestBody MonitorServerGroupStatus monitorStatus) {
    	MonitorServerGroupStatus updatedMonitorStatus = msService.updateMonitorStatus(monitorStatus); 
        
        return new ResponseEntity<>(updatedMonitorStatus, HttpStatus.OK);
    }

    @GetMapping("/get-monitor-server-group-status")
    public ResponseEntity<List<MonitorServerGroupStatus>> getMonitorStatuss() {
        List<MonitorServerGroupStatus> MonitorStatuss = msService.getMonitorStatuses();  
        return new ResponseEntity<>(MonitorStatuss, HttpStatus.OK);
    }
    
    @GetMapping("/get-monitor-server-group-status-by/{id}")
    public ResponseEntity<MonitorServerGroupStatus> getMonitorStatus(@PathVariable Long id) {
    	MonitorServerGroupStatus MonitorStatus = msService.getMonitorStatus(id);  
        return new ResponseEntity<>(MonitorStatus, HttpStatus.OK);
    }

    @DeleteMapping("/delete-monitor-server-group-status-by/{id}")
    public ResponseEntity<MonitorServerGroupStatus> deleteMonitorStatusDetails(@PathVariable Long id) {
    	MonitorServerGroupStatus dMonitorStatus = msService.deleteMonitorStatus(id);
        return new ResponseEntity<>(dMonitorStatus, HttpStatus.OK);
    }
}
