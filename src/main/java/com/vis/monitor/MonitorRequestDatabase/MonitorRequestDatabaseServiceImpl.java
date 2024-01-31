package com.vis.monitor.MonitorRequestDatabase;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorRequestDatabaseServiceImpl implements MonitorRequestDatabaseService{

	
	@Autowired
	private MonitorRequestDatabaseRepository mrdbRepo;
	
	@Override
	public MonitorRequestDatabase addMonitorRequestDatabase(MonitorRequestDatabase monitorRequestDatabase) {
		// TODO Auto-generated method stub
		
		return mrdbRepo.save(monitorRequestDatabase) ;
	}

	@Override
	public MonitorRequestDatabase getMonitorRequestDatabase(Long id) {
		// TODO Auto-generated method stub
 Optional<MonitorRequestDatabase>monitorRequestDatabase = mrdbRepo.findById(id);
	return monitorRequestDatabase.isPresent()? monitorRequestDatabase.get():null;
	}

	@Override
	public MonitorRequestDatabase updateMonitorRequestDatabase(MonitorRequestDatabase monitorRequestDatabase) {
		// TODO Auto-generated method stub
		return mrdbRepo.save(monitorRequestDatabase);
	}

	@Override
	public MonitorRequestDatabase deleteMonitorRequestDatabase(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorRequestDatabase>monitorRequestDatabase=mrdbRepo.findById(id);
		if(monitorRequestDatabase.isPresent())
		{
		mrdbRepo.deleteById(id);	
		}
		
		return monitorRequestDatabase.isPresent() ? monitorRequestDatabase.get():null;
	}

	@Override
	public List<MonitorRequestDatabase> getMonitorRequestDatabases() {
		// TODO Auto-generated method stub
		List<MonitorRequestDatabase> findAll=mrdbRepo.findAll();
		return findAll;
	}

	
}
