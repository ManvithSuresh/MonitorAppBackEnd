package com.vis.monitor.server.group.monitor.status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.User;
@Service
public class MontiorServerGroupServiceImpl  implements MonitorServerGroupStatusService{
	

	@Autowired
	private MonitorServerGroupStatusRepository msRepo;
	
	@Override
	public List<MonitorServerGroupStatus> getMonitorStatusesByUser(User user) {
		// TODO Auto-generated method stub
		return msRepo.findByUser(user);
	}

	@Override
	public MonitorServerGroupStatus addMonitorStatus(MonitorServerGroupStatus monitorStatus) {
		// TODO Auto-generated method stub
		return msRepo.save(monitorStatus);
	}

	@Override
	public MonitorServerGroupStatus updateMonitorStatus(MonitorServerGroupStatus monitorStatus) {
		// TODO Auto-generated method stub
		return msRepo.save(monitorStatus);
	}

	@Override
	public List<MonitorServerGroupStatus> getMonitorStatuses() {
		// TODO Auto-generated method stub
		return msRepo.findAll();
	}

	@Override
	public MonitorServerGroupStatus getMonitorStatus(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorServerGroupStatus> oMonitorStatus = msRepo.findById(id);
		return oMonitorStatus.isPresent() ? oMonitorStatus.get() : null;
	}

	@Override
	public MonitorServerGroupStatus deleteMonitorStatus(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorServerGroupStatus> oMonitorStatus = msRepo.findById(id);
		if(oMonitorStatus.isPresent()) {
			msRepo.deleteById(id);
		}
		return oMonitorStatus.isPresent() ? oMonitorStatus.get() : null;
	}

}
