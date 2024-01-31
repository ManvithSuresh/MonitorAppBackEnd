package com.vis.monitor.scheduler;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.MonitorRequest;

import lombok.extern.log4j.Log4j2;

import java.util.List;
@Log4j2
@Service
public class MonitorServerGroupServiceImpl implements MonitorServerGroupService {

    @Autowired
    private MonitorServerGroupRepository monitorServerGroupRepository;

    @Override
    public List<MonitorServerGroup> getAllMonitorServerGroups() {
        return monitorServerGroupRepository.findAll();
    }

    @Override
    public MonitorServerGroup getMonitorServerGroupById(Long id) {
        return monitorServerGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MonitorServerGroup not found with id: " + id));
    }

    @Override
    public MonitorServerGroup addMonitorServerGroup(MonitorServerGroup monitorServerGroup) {
    return monitorServerGroupRepository.save(monitorServerGroup);
    }
    
    @Override
	public MonitorServerGroup updateMonitorServerGroup(MonitorServerGroup monitorRequest) {
		// TODO Auto-generated method stub
		return monitorServerGroupRepository.save(monitorRequest);
	}


    @Override
    public void deleteMonitorServerGroup(Long id) {
        monitorServerGroupRepository.deleteById(id);
    }
}

