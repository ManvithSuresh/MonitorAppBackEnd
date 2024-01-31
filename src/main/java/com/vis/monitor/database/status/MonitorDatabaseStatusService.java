package com.vis.monitor.database.status;

import java.util.List;

import com.vis.monitor.modal.MonitorStatus;
import com.vis.monitor.modal.User;

public interface MonitorDatabaseStatusService {
	
public List<MonitorDatabaseStatus> getMonitorDatabaseStatusesByUser(User user);
	
	public MonitorDatabaseStatus addMonitorDatabaseStatus(MonitorDatabaseStatus monitorDatabaseStatus);

	public MonitorDatabaseStatus updateMonitorDatabaseStatus(MonitorDatabaseStatus monitorDatabaseStatus);

	public List<MonitorDatabaseStatus> getMonitorDatabaseStatuses();

	public MonitorDatabaseStatus getMonitorDatabaseStatus(Long id);

	public MonitorDatabaseStatus deleteMonitorDatabaseStatus(Long id);
}
