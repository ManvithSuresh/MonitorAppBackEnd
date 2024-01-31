package com.vis.monitor.MonitorRequestWebService;

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
public class MontiorRequestWebServiceController {

	@Autowired
	private MonitorRequestWebServiceService mrwebService;
	
	@PostMapping("/add-monitorwebservice-request")
	public ResponseEntity<MonitorRequestWebService>addMonitorRequestWebService(@RequestBody MonitorRequestWebService mrReqService	){
		MonitorRequestWebService savedMontiorRequestWebService = mrwebService.addMonitorRequestWebService(mrReqService);
		return new ResponseEntity<>(savedMontiorRequestWebService,HttpStatus.CREATED);
				
	}
	
	@PutMapping("/update-monitorwebservice-request")
	public ResponseEntity<MonitorRequestWebService>updateMonitorRequestWebService(@RequestBody MonitorRequestWebService mrReqService){
		MonitorRequestWebService updaMonitorRequestWebService=mrwebService.updateMonitorRequestWebService(mrReqService);
		return new ResponseEntity<>(updaMonitorRequestWebService,HttpStatus.OK);
	}
	
	@GetMapping("/get-monitorwebservice-request")
	public ResponseEntity<List<MonitorRequestWebService>> getMonitorRequestWebservice(){
		List<MonitorRequestWebService>mrReqService=mrwebService.getMonitorRequestWebServices();
		return new ResponseEntity<>(mrReqService,HttpStatus.OK);
				}
	
	
	@GetMapping("/get-monitorwebservice-request/{id}")
	public ResponseEntity<MonitorRequestWebService>getMonitorRequestWebservice(@PathVariable Long id){
MonitorRequestWebService mrReqService=mrwebService.getMonitorRequestWebService(id);
return new ResponseEntity<>(mrReqService,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-monitorwebservice-request/{id}")
	public ResponseEntity<MonitorRequestWebService> deleteMonitorWebserviceRequestDetails(@PathVariable Long id ){
		MonitorRequestWebService mrReqService=mrwebService.deleteMonitorRequestWebService(id);
		return new ResponseEntity<>(mrReqService,HttpStatus.OK);
		
}
}
