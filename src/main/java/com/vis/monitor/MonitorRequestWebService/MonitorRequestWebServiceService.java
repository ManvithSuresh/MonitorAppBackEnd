package com.vis.monitor.MonitorRequestWebService;

import java.util.List;

public interface MonitorRequestWebServiceService {
	
	 MonitorRequestWebService addMonitorRequestWebService(MonitorRequestWebService mrsService);
     MonitorRequestWebService getMonitorRequestWebService(Long id);
     MonitorRequestWebService updateMonitorRequestWebService(MonitorRequestWebService mrsService);
     MonitorRequestWebService deleteMonitorRequestWebService(Long id);
     List<MonitorRequestWebService> getMonitorRequestWebServices();

}
