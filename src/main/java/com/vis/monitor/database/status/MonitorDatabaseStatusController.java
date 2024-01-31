package com.vis.monitor.database.status;

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
@RequestMapping("/api")
public class MonitorDatabaseStatusController {

    @Autowired
    private MonitorDatabaseStatusService msService;

    @PostMapping("/add-monitorDatabase-status")
    public ResponseEntity<MonitorDatabaseStatus> addMonitorStatus(@RequestBody MonitorDatabaseStatus monitorDatabaseStatus) {
        MonitorDatabaseStatus savedMonitorDatabaseStatus = msService.addMonitorDatabaseStatus(monitorDatabaseStatus);
        return new ResponseEntity<>(savedMonitorDatabaseStatus, HttpStatus.CREATED);
    }

    @PutMapping("/update-monitorDatabase-status")
    public ResponseEntity<MonitorDatabaseStatus> updateMonitorStatus(@RequestBody MonitorDatabaseStatus monitorDatabaseStatus) {
        MonitorDatabaseStatus updatedMonitorDatabaseStatus = msService.updateMonitorDatabaseStatus(monitorDatabaseStatus);
        return new ResponseEntity<>(updatedMonitorDatabaseStatus, HttpStatus.OK);
    }

    @GetMapping("/get-monitorDatabase-statuses")
    public ResponseEntity<List<MonitorDatabaseStatus>> getMonitorStatuses() {
        List<MonitorDatabaseStatus> monitorDatabaseStatuses = msService.getMonitorDatabaseStatuses();
        return new ResponseEntity<>(monitorDatabaseStatuses, HttpStatus.OK);
    }

    @GetMapping("/get-monitorDatabase-status/{id}")
    public ResponseEntity<MonitorDatabaseStatus> getMonitorStatus(@PathVariable Long id) {
        MonitorDatabaseStatus monitorDatabaseStatus = msService.getMonitorDatabaseStatus(id);
        return new ResponseEntity<>(monitorDatabaseStatus, HttpStatus.OK);
    }

    @DeleteMapping("/delete-monitorDatabase-status/{id}")
    public ResponseEntity<MonitorDatabaseStatus> deleteMonitorStatusDetails(@PathVariable Long id) {
        MonitorDatabaseStatus deletedMonitorDatabaseStatus = msService.deleteMonitorDatabaseStatus(id);
        return new ResponseEntity<>(deletedMonitorDatabaseStatus, HttpStatus.OK);
    }
}
