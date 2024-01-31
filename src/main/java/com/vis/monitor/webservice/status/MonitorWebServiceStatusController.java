package com.vis.monitor.webservice.status;

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
public class MonitorWebServiceStatusController {


	@Autowired
	private MonitorWebServiceStatusService mwebService;
	
@PostMapping("/add-monitorWebService-status")
public ResponseEntity<MonitorWebServiceStatus>addMonitorWebServiceStatus(@RequestBody MonitorWebServiceStatus monitorWebServiceStatus){
	MonitorWebServiceStatus savedMonitorWebServiceStatus =mwebService.addMonitorWebServiceStatus(monitorWebServiceStatus);
	return new ResponseEntity<>(savedMonitorWebServiceStatus,HttpStatus.CREATED);
	
}

@PutMapping("/update-monitorWebService-status")
public ResponseEntity<MonitorWebServiceStatus> updateMonitorWebServiceStatus(@RequestBody MonitorWebServiceStatus monitorWebServiceStatus) {
    MonitorWebServiceStatus updatedMonitorWebServiceStatus =mwebService.updateMonitorWebServiceStatus(monitorWebServiceStatus);
    return new ResponseEntity<>(updatedMonitorWebServiceStatus, HttpStatus.OK);
}

@GetMapping("/get-monitorWebService-status")
public ResponseEntity<List<MonitorWebServiceStatus>> getMonitorWebServiceStatuses() {
    List< MonitorWebServiceStatus>getMonitorWebServiceStatuses = mwebService.getMonitorWebServiceStatus();
    return new ResponseEntity<>(getMonitorWebServiceStatuses, HttpStatus.OK);
}

@GetMapping("/get-monitorWebService-status/{id}")
public ResponseEntity<MonitorWebServiceStatus> getMonitorWebServiceStatus(@PathVariable Long id) {
    MonitorWebServiceStatus getByIDMonitorWebServiceStatus = mwebService.getMonitorWebServiceStatus(id);
    return new ResponseEntity<>( getByIDMonitorWebServiceStatus , HttpStatus.OK);
}

@DeleteMapping("/delete-monitorWebService-status/{id}")
public ResponseEntity<MonitorWebServiceStatus> deleteMonitorWebServiceStatusDetails(@PathVariable Long id) {
    MonitorWebServiceStatus deletedMonitorWebServiceStatus =mwebService.deleteMonitorWebServiceStatus(id);
    return new ResponseEntity<>(deletedMonitorWebServiceStatus, HttpStatus.OK);
}
}


