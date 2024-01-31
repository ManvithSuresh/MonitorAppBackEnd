package com.vis.monitor.scheduler;


import java.util.List;

public interface MonitorServerGroupService {

    List<MonitorServerGroup> getAllMonitorServerGroups();

    MonitorServerGroup getMonitorServerGroupById(Long id);

    MonitorServerGroup addMonitorServerGroup(MonitorServerGroup monitorServerGroup);

    MonitorServerGroup updateMonitorServerGroup( MonitorServerGroup monitorServerGroup);

    void deleteMonitorServerGroup(Long id);
}

