package com.vis.monitor.service;

import java.util.Date;
import java.util.List;

import com.vis.monitor.ws.modal.WebService;
import com.vis.monitor.ws.modal.WebServiceStatus;

public interface WebServiceStatusService {
	    WebServiceStatus getWebServiceStatus(Long id);
	    List<WebServiceStatus> getAllWebServiceStatuses();
	    WebServiceStatus addWebServiceStatus(WebServiceStatus webServiceStatus);
	    WebServiceStatus updateWebServiceStatus(Long id, WebServiceStatus webServiceStatus);
	    void deleteWebServiceStatus(Long id);
	    WebServiceStatus getTopWebServiceStatusByWebService(WebService webService);
	    List<WebServiceStatus> getWebServiceStatusByWebServiceAndStartTimeAndEndTime(
	            WebService webService, Date startDate, Date endDate);
	    WebServiceStatus getWebServiceStatusByWebService(WebService webService);
		
		
	}

