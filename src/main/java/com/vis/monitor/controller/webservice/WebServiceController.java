package com.vis.monitor.controller.webservice;

import com.vis.monitor.ws.modal.WebService;
import com.vis.monitor.ws.modal.WebServiceHourlyStatus;
import com.vis.monitor.ws.modal.WebServiceRequest;
import com.vis.monitor.ws.modal.WebServiceResponse;
import com.vis.monitor.ws.modal.WebServiceStatus;

import com.vis.monitor.service.WSService;
import com.vis.monitor.service.WebServiceStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class WebServiceController {

	@Autowired
    private WSService wsService;
	

	@Autowired
	private WebServiceStatusService webservicestatusservice;

    @PostMapping("/add-web-service")
    public ResponseEntity<WebService> addWebService(@RequestBody WebService webService) {
    	WebService sWebService = wsService.addWebService(webService);         
        return new ResponseEntity<>(sWebService, HttpStatus.CREATED);
    }
    
    @PutMapping("/update-web-service")
    public ResponseEntity<WebService> updateWebService(@RequestBody WebService webService) {
    	WebService sWebService = wsService.updateWebService(webService);         
        return new ResponseEntity<>(sWebService, HttpStatus.OK);
    }

    @GetMapping("/get-web-services")
    @Transactional
    public ResponseEntity<List<WebService>> getWebServices() {
        List<WebService> webServices = wsService.getWebServices();  
        return new ResponseEntity<>(webServices, HttpStatus.OK);
    }

    @GetMapping("/get-web-service/{id}")
    public ResponseEntity<WebService> getWebService(@PathVariable Long id) {
    	WebService gWebService = wsService.getWebService(id);
        return new ResponseEntity<>(gWebService, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete-web-service/{id}")
    public ResponseEntity<WebService> deleteWebService(@PathVariable Long id) {
    	WebService dWebService = wsService.deleteWebService(id);
        return new ResponseEntity<>(dWebService, HttpStatus.OK);
    }
    
    @PostMapping("/execute-web-service")
    public ResponseEntity<WebServiceResponse> executeWebService(@RequestBody WebServiceRequest request) {
    	WebServiceResponse gWebService = wsService.executeWebService(request);
        return new ResponseEntity<>(gWebService, HttpStatus.OK);
    }
    
    
    @GetMapping("/get-webservice-status/{id}")
    public ResponseEntity<WebServiceStatus> getWebServiceStatus(@PathVariable Long id) {
        WebService webService= wsService.getWebService(id);
        if (webService != null) {
            WebServiceStatus webserviceStatus = webservicestatusservice.getTopWebServiceStatusByWebService(webService);

            if (webserviceStatus != null) {
                return ResponseEntity.ok(webserviceStatus);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/get-webservice-hourly-status/{id}")
    public ResponseEntity<List<WebServiceHourlyStatus>> getWebServiceHourlyStatusByWebServiceId(@PathVariable Long id) {
        List<WebServiceHourlyStatus> hourlyStatus = new ArrayList<>();

        Map<String, WebServiceHourlyStatus> hmHourStatus = new LinkedHashMap<>();

        WebService webService = wsService.getWebService(id);

        if (webService != null) {
            LocalDateTime now = LocalDateTime.now();

            String strDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
            String strEndTime = strDate + ":59:59";
            Date endDate = new Date();

            try {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            now = now.minusDays(1);

            strDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
            String strStartTime = strDate + ":00:00";
            Date startDate = new Date();

            try {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            List<WebServiceStatus> webServiceStatuses = webservicestatusservice.getWebServiceStatusByWebServiceAndStartTimeAndEndTime(webService, startDate, endDate);

            Date stDate = startDate;
            Date stDateBackup = startDate;

            String nowDateHour = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
            String sDateHour = new SimpleDateFormat("yyyy-MM-dd HH").format(stDate);

            while (!nowDateHour.equals(sDateHour)) {
                Integer hour = null;
                Long duration = null;

                for (WebServiceStatus status : webServiceStatuses) {
                    Date downAt = status.getDownAt();
                    Date upAt = status.getUpAt() != null ? status.getUpAt() : new Date();
                    boolean isDownTimeFound = false;
                    WebServiceHourlyStatus hourStatus = null;

                    while (!nowDateHour.equals(sDateHour)) {
                        hourStatus = null;

                        Instant iNextHour = stDate.toInstant().plusSeconds(((59 * 60) + 59));
                        Date nextHour = Date.from(iNextHour);

                        boolean das = downAt.after(stDate);
                        boolean dbs = downAt.before(stDate);
                        boolean dbn = downAt.before(nextHour);
                        boolean des = downAt.equals(stDate);
                        boolean den = downAt.equals(nextHour);

                        boolean uas = upAt.after(stDate);
                        boolean ubn = upAt.before(nextHour);
                        boolean uen = upAt.equals(nextHour);

                        sDateHour = new SimpleDateFormat("yyyy-MM-dd HH").format(stDate);
                        String sHour = new SimpleDateFormat("HH").format(stDate);
                        hour = Integer.parseInt(sHour);

                        duration = isDownTimeFound ? (nextHour.getTime() - stDate.getTime()) : 0L;

                        if ((dbs || des) && (dbn || den)) {
                            duration = nextHour.getTime() - stDate.getTime();
                            isDownTimeFound = true;
                        }

                        if ((das || des) && dbn) {
                            duration = nextHour.getTime() - downAt.getTime();
                            isDownTimeFound = true;
                        }

                        if (uas && (ubn || uen)) {
                            if ((das || des) && dbn) {
                                duration = upAt.getTime() - downAt.getTime();
                                isDownTimeFound = true;
                            } else {
                                duration = upAt.getTime() - stDate.getTime();
                            }
                            stDateBackup = stDate;
                            break;
                        }

                        stDateBackup = stDate;

                        Instant iStartDate = nextHour.toInstant().plusSeconds(1);
                        stDate = Date.from(iStartDate);

                        iNextHour = stDate.toInstant().plusSeconds(((59 * 60) + 59));
                        nextHour = Date.from(iNextHour);

                        long diffInMillies = Math.abs(duration);
                        long seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        seconds = seconds == 0 ? 0 : seconds + 1;

                        if (hmHourStatus.containsKey(sDateHour)) {
                            hourStatus = hmHourStatus.get(sDateHour);
                            seconds = hourStatus.getDuration() + seconds;
                        }

                        if (hourStatus == null) {
                            hourStatus = new WebServiceHourlyStatus(stDateBackup, stDateBackup, hour, seconds);
                        }

                        hmHourStatus.put(sDateHour, hourStatus);
                    }

                    long diffInMillies = Math.abs(duration);
                    long seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    seconds = seconds == 0 ? 0 : seconds + 1;

                    if (hmHourStatus.containsKey(sDateHour)) {
                        hourStatus = hmHourStatus.get(sDateHour);
                        seconds = hourStatus.getDuration() + seconds;
                    }

                    if (hourStatus == null) {
                        hourStatus = new WebServiceHourlyStatus(stDateBackup, stDateBackup, hour, seconds);
                    }

                    hmHourStatus.put(sDateHour, hourStatus);
                }
            }

            hourlyStatus.addAll(hmHourStatus.values());

            return ResponseEntity.ok(hourlyStatus);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}