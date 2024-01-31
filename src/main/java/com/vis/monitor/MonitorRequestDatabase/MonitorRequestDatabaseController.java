package com.vis.monitor.MonitorRequestDatabase;

import java.security.PublicKey;
import java.util.List;import javax.net.ssl.HttpsURLConnection;

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
public class MonitorRequestDatabaseController {

	
	@Autowired
	private MonitorRequestDatabaseService mrdbService;
	
	
	@PostMapping("/add-monitorDatabase-request")
	public ResponseEntity<MonitorRequestDatabase> addMonitorRequestDatabase(@RequestBody MonitorRequestDatabase monitorRequestDatabase){
		MonitorRequestDatabase savedMonitorRequestDatabase=mrdbService.addMonitorRequestDatabase(monitorRequestDatabase);
		return new ResponseEntity<>(savedMonitorRequestDatabase,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update-monitorDatabase-request")
	public ResponseEntity<MonitorRequestDatabase>updateMonitorRequestDatabase(@RequestBody MonitorRequestDatabase monitorRequestDatabase){
		MonitorRequestDatabase updateMonitorRequestDatabase=mrdbService.updateMonitorRequestDatabase(monitorRequestDatabase);
		return new ResponseEntity<>(updateMonitorRequestDatabase,HttpStatus.OK);
		
	}
	
	@GetMapping("/get-monitorDatabase-request")
	public ResponseEntity<List<MonitorRequestDatabase>> getMonitorRequestsDatabase(){
		List<MonitorRequestDatabase> monitorRequestDatabases=mrdbService.getMonitorRequestDatabases();
		return new ResponseEntity<>(monitorRequestDatabases,HttpStatus.OK);
		
	}
	
	@GetMapping("/get-monitorDatabase-request/{id}")
	public ResponseEntity<MonitorRequestDatabase> getMonitorRequestDatabase(@PathVariable Long id){
		MonitorRequestDatabase monitorRequestDatabase =mrdbService.getMonitorRequestDatabase(id);
		return new ResponseEntity<>(monitorRequestDatabase,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete-monitorDatabase-request/{id}")
	public ResponseEntity<MonitorRequestDatabase> deleteMonitorRequestDetails(@PathVariable Long id ){
		MonitorRequestDatabase deleteMonitorRequestDatabase=mrdbService.deleteMonitorRequestDatabase(id);
		return new ResponseEntity<>(deleteMonitorRequestDatabase,HttpStatus.OK);
		
	}
}
