//package com.vis.monitor.scheduler;
//
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.vis.monitor.MonitorRequestWebService.MonitorRequestWebService;
//import com.vis.monitor.MonitorRequestWebService.MonitorRequestWebServiceService;
//
//import com.vis.monitor.modal.User;
//import com.vis.monitor.repository.WebServiceRepository;
//import com.vis.monitor.service.EmailService;
//
//import com.vis.monitor.service.WebServiceStatusService;
//import com.vis.monitor.webservice.status.MonitorWebServiceStatus;
//import com.vis.monitor.webservice.status.MonitorWebServiceStatusService;
//import com.vis.monitor.ws.modal.WebService;
//import com.vis.monitor.ws.modal.WebServiceStatus;
//
//import lombok.extern.log4j.Log4j2;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//@Component
//@Log4j2
//@Transactional
//public class WebserviceScheduler {
//
//@Autowired
//private EmailService emailService;
//
//@Autowired 
//private MonitorRequestWebServiceService mrService;
//
//@Autowired
//private MonitorWebServiceStatusService mwebService;
//
//@Autowired 
//private WebServiceStatusService webStatusService;
//
//@Autowired
//private WebServiceRepository  webServiceRepository;
//
//private Map<WebService,Boolean>prvWebServiceMap=new HashMap<>();
//
//	
//	
//	@Scheduled(fixedRate=10000)
//	public void checkAllWebservicesAndSendMails() {
//		
//		log.info("Scheduled task to monitor webservice has started");
//		
//		List<MonitorRequestWebService> monitorRequests= mrService.getMonitorRequestWebServices();
//	    log.info("total requests"+ monitorRequests.size());
//		  
//	    if (monitorRequests != null && !monitorRequests.isEmpty()) {
//	        log.info("Total requests: {}", monitorRequests.size());
//
//	        for (MonitorRequestWebService monitorRequest : monitorRequests) {
//	            WebService webservice = monitorRequest.getWebservice();
//	            log.info("Webservices: {}", webservice);
//
//	            boolean isWebServiceNotReachable = checkingWebService(webservice);
//	            log.warn("Database is null for monitorRequest: {}", monitorRequest);
//
//	            boolean wasWebServiceReachable = false;
//	            log.info("WebService {} - Current Reachable: {}, Was Reachable: {}", webservice, isWebServiceNotReachable);
//	            webservice.setIsActive(!isWebServiceNotReachable);
//	            webServiceRepository.save(webservice);
//
//	            if (prvWebServiceMap.containsKey(webservice)) {
//	                wasWebServiceReachable = prvWebServiceMap.get(webservice);
//	                log.info("The status is: {}", wasWebServiceReachable);
//	            } else {
//	                WebServiceStatus wbStatusService = webStatusService.getTopWebServiceStatusByWebService(webservice);
//	                wasWebServiceReachable = (wbStatusService != null && wbStatusService.getUpAt() != null);
//	                log.info("The message is: {}", wasWebServiceReachable);
//	            }
//
//	            webservice.setIsActive(!isWebServiceNotReachable);
//	            webServiceRepository.save(webservice);
//
//	            if (!isWebServiceNotReachable || wasWebServiceReachable) {
//	                log.warn("Webservice {} is not reachable. URL: {}", webservice.getUrl());
//
//	                WebServiceStatus wbWebserviceStatus = webStatusService.getWebServiceStatusByWebService(webservice);
//	                if (wbWebserviceStatus == null) {
//	                    wbWebserviceStatus = new WebServiceStatus();
//	                    wbWebserviceStatus.setWebService(webservice);
//	                    wbWebserviceStatus.setDownAt(new Date());
//	                    webStatusService.addWebServiceStatus(wbWebserviceStatus);
//	                }
//
//	                List<User> users = monitorRequest.getUsers();
//	                log.info("Total users: {}", users);
//
//	                if (users != null) {
//	                    for (User user : users) {
//	                        sendWebServiceNotReachableEmail(user, webservice);
//	                    }
//	                } else {
//	                    log.warn("No user details found for webservice {}", webservice.getName());
//	                }
//	            } else if (isWebServiceNotReachable || !wasWebServiceReachable) {
//	                log.info("Webservice {} is reachable again. URL: {}", webservice.getName(), webservice.getUrl());
//
//	                WebServiceStatus wbWebserviceStatus = webStatusService.getWebServiceStatusByWebService(webservice);
//	                if (wbWebserviceStatus != null && wbWebserviceStatus.getUpAt() == null) {
//	                    Date upAt = new Date();
//	                    wbWebserviceStatus.setUpAt(upAt);
//
//	                    long diffInMillies = Math.abs(upAt.getTime() - wbWebserviceStatus.getDownAt().getTime());
//	                    long duration = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//	                    wbWebserviceStatus.setDuration(duration);
//	                    webStatusService.updateWebServiceStatus(null, wbWebserviceStatus);
//	                }
//
//	                List<User> users = monitorRequest.getUsers();
//	                if (users != null) {
//	                    for (User user : users) {
//	                        sendWebServiceReachableEmail(user, webservice);
//	                    }
//	                } else {
//	                    log.warn("No user details found for webservice {}", webservice.getName());
//	                }
//	            }
//
//	            prvWebServiceMap.put(webservice, isWebServiceNotReachable);
//	        }
//
//	        log.info("Scheduled task to monitor webservice completed.");
//	    } else {
//	        log.info("No monitor requests found for webservice Skipping monitoring.");
//	    }
//	}     
//		            
//			 
//			 
//			 
//    private boolean checkingWebService(WebService webService) {
//        log.info("calling checkingwebservice method");
//        if (webService != null) {
//            String url = webService.getUrl();
//            if (url != null) {
//                return checkingWebService(url);
//            }
//        }
//        return false;
//    }
//
//    private boolean checkingWebService(String url) {
//        try {
//            URL serviceUrl = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
//            connection.setConnectTimeout(5000);
//            connection.connect();
//
//            int responseCode = connection.getResponseCode();
//            return responseCode != HttpURLConnection.HTTP_OK;
//        } catch (IOException e) {
//            log.error("Error connecting to the webservice {}: {}", url, e.getMessage());
//            return true;
//        }
//    }
//	
//	private void  sendWebServiceReachableEmail( User user, WebService webservice) {
//		 List<MonitorWebServiceStatus> monitorWebServiceStatuses = mwebService.getMonitorWebServiceStatusesByUser(user);
//boolean isSent=false;
//
//for (MonitorWebServiceStatus monitorWebserviceStatus :monitorWebServiceStatuses) {
//	
//if (monitorWebserviceStatus.getIsSent()){
//	isSent=true;
//	log.info("Email already sent to {} for reachable webservice.", user);
//    break;
//}
//	}
//
//if(!isSent) {
//	MonitorWebServiceStatus monitorWebserviceStatus =new MonitorWebServiceStatus();
//	
//	try {
//		String subject="webservice Reacble Alert";
//        String message = "The URL " + webservice.getUrl() + " is reachable again.";
//
//        isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//        monitorWebserviceStatus.setUser(user);
//        monitorWebserviceStatus.setIsSent(isSent);
//        monitorWebserviceStatus.setSentAt(new Date());
//        monitorWebserviceStatus.setWebservice(webservice);
//
//        log.info("Email notification sent to {} successfully.", user);
//    } catch (Exception ex) {
//        log.error("Failed to send email notification to {} for reachable webservice: {}", user, ex.getMessage());
//    }
//
//	mwebService.addMonitorWebServiceStatus(monitorWebserviceStatus);
//}
//
//	}
//	
//	
//	private void sendWebServiceNotReachableEmail(User user, WebService webservice) {
//	    List<MonitorWebServiceStatus> monitorWebServiceStatuses = mwebService.getMonitorWebServiceStatusesByUser(user);
//	    boolean isSent = false;
//
//	    for (MonitorWebServiceStatus monitorWebserviceStatus : monitorWebServiceStatuses) {
//	        if (monitorWebserviceStatus.getIsSent()) {
//	            isSent = true;
//	            log.info("Email already sent to {} for not reachable webservice.", user.getEMail());
//	            break;
//	        }
//	    }
//
//	    if (!isSent) {
//	        MonitorWebServiceStatus monitorWebserviceStatus = new MonitorWebServiceStatus();
//
//	        try {
//	            String subject = "Webservice Not Reachable Alert";
//	            String message = "The URL " + webservice.getUrl() + " is not reachable.";
//
//	            isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//	            monitorWebserviceStatus.setUser(user);
//	            monitorWebserviceStatus.setIsSent(isSent);
//	            monitorWebserviceStatus.setSentAt(new Date());
//	            monitorWebserviceStatus.setWebservice(webservice);
//
//	            log.info("Email notification sent to {} successfully.", user.getEMail());
//	        } catch (Exception ex) {
//	            log.error("Failed to send email notification to {} for not reachable webservice: {}", user.getEMail(),
//	                    ex.getMessage());
//	        }
//
//	        mwebService.addMonitorWebServiceStatus(monitorWebserviceStatus);
//	    }
//	}
//}
//
//
