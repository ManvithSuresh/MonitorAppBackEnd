package com.vis.monitor.server.group.status;

import java.util.Date;
import java.util.List;

import com.vis.monitor.modal.ServerGroup;

public interface ServerGroupStatusService {
	
	
	public ServerGroupStatus addServerStatus(ServerGroupStatus server);

	public ServerGroupStatus updateServerStatus(ServerGroupStatus server);

	public List<ServerGroupStatus> getServerStatuses();

	public ServerGroupStatus getServerStatusById(Long id);
	
	public ServerGroupStatus getServerStatusByServer(ServerGroup server);
	
	public ServerGroupStatus getTopServerStatusByServer(ServerGroup server);

	public ServerGroupStatus deleteServerStatus(Long id);
	
	public List<ServerGroupStatus> getServerStatusByServerAndStartTimeAndEndTime(ServerGroup server, Date startDate, Date endDate);

}