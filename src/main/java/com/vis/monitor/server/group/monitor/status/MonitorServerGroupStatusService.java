package com.vis.monitor.server.group.monitor.status;

import java.util.List;




import com.vis.monitor.modal.User;

public interface MonitorServerGroupStatusService {
	public List<MonitorServerGroupStatus> getMonitorStatusesByUser(User user);
	
	public MonitorServerGroupStatus addMonitorStatus(MonitorServerGroupStatus monitorStatus);

	public MonitorServerGroupStatus updateMonitorStatus(MonitorServerGroupStatus monitorStatus);

	public List<MonitorServerGroupStatus> getMonitorStatuses();

	public MonitorServerGroupStatus getMonitorStatus(Long id);

	public MonitorServerGroupStatus deleteMonitorStatus(Long id);
	
}
