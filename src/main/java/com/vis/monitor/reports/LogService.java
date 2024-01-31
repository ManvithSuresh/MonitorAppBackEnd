package com.vis.monitor.reports;

import java.util.List;

public interface LogService {

	public List<Logs> getLogsByLevelAndTime(String level, String startTime, String endTime, int page, int pageSize) ;
	List<Logs> getLogsByLevel(String level);

}
