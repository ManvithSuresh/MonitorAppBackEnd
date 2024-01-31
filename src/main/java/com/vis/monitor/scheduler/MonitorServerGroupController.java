package com.vis.monitor.scheduler;



	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/api")
	public class MonitorServerGroupController {

	    @Autowired
	    private MonitorServerGroupService monitorServerGroupService;

	    @GetMapping("/get-all-monitor-server-group")
	    public ResponseEntity <List<MonitorServerGroup>> getAllMonitorServerGroups() {
	    	List<MonitorServerGroup> monitorServerGroupRequest = monitorServerGroupService.getAllMonitorServerGroups();
	        return new ResponseEntity<> (monitorServerGroupRequest ,HttpStatus.OK);
	        
	    }

	    @GetMapping("/monitor-server-group-by/{id}")
	    public MonitorServerGroup getMonitorServerGroupById(@PathVariable Long id) {
	        return monitorServerGroupService.getMonitorServerGroupById(id);
	    }

	    @PostMapping("/add-monitor-server-group")
	    public ResponseEntity<MonitorServerGroup>addMonitorServerGroup(@RequestBody MonitorServerGroup monitorServerGroup) {
MonitorServerGroup savedMonitorServerGroupRequest =    	 monitorServerGroupService.addMonitorServerGroup(monitorServerGroup);
	    	return new ResponseEntity <>( savedMonitorServerGroupRequest ,HttpStatus.CREATED);
	    }

	    @PutMapping("update-monitor-server-group")
	    public MonitorServerGroup updateMonitorServerGroup( @RequestBody MonitorServerGroup monitorServerGroup) {
	        return monitorServerGroupService.updateMonitorServerGroup( monitorServerGroup);
	    }

	    @DeleteMapping("delete-monitor-server-group/by/{id}")
	    public void deleteMonitorServerGroup(@PathVariable Long id) {
	        monitorServerGroupService.deleteMonitorServerGroup(id);
	    }
	}
