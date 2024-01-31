package com.vis.monitor.webservice.status;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.User;

@Service
public interface  MonitorWebServiceStatusService  {

	List<MonitorWebServiceStatus> getMonitorWebServiceStatusesByUser(User user);

	MonitorWebServiceStatus addMonitorWebServiceStatus(MonitorWebServiceStatus monitorWebserviceStatus);

	

	List<MonitorWebServiceStatus> getMonitorWebServiceStatus();

	MonitorWebServiceStatus getMonitorWebServiceStatus(Long id);

	MonitorWebServiceStatus deleteMonitorWebServiceStatus(Long id);

	MonitorWebServiceStatus updateMonitorWebServiceStatus(MonitorWebServiceStatus monitorWebServiceStatus);
	
	
}