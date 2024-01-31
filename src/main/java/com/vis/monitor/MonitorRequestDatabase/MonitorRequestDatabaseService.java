package com.vis.monitor.MonitorRequestDatabase;

import java.util.List;

public interface MonitorRequestDatabaseService {

	public MonitorRequestDatabase addMonitorRequestDatabase(MonitorRequestDatabase monitorRequestDatabase);
	
	public MonitorRequestDatabase getMonitorRequestDatabase(Long id);
	
	public MonitorRequestDatabase updateMonitorRequestDatabase(MonitorRequestDatabase monitorRequestDatabase);
	
	public MonitorRequestDatabase deleteMonitorRequestDatabase(Long id );
	
	public List<MonitorRequestDatabase> getMonitorRequestDatabases();
	}
