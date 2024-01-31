package com.vis.monitor.service.impl;

import com.vis.monitor.ws.modal.WebService;
import com.vis.monitor.ws.modal.WebServiceStatus;
import com.vis.monitor.repository.WebServiceStatusRepository;
import com.vis.monitor.service.WebServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WebServiceStatusServiceImpl implements WebServiceStatusService {

    private final WebServiceStatusRepository webServiceStatusRepository;

    @Autowired
    public WebServiceStatusServiceImpl(WebServiceStatusRepository webServiceStatusRepository) {
        this.webServiceStatusRepository = webServiceStatusRepository;
    }

    @Override
    public WebServiceStatus getWebServiceStatus(Long id) {
        Optional<WebServiceStatus> optionalWebServiceStatus = webServiceStatusRepository.findById(id);
        return optionalWebServiceStatus.orElse(null);
    }

    @Override
    public List<WebServiceStatus> getAllWebServiceStatuses() {
        return webServiceStatusRepository.findAll();
    }

    @Override
    public WebServiceStatus addWebServiceStatus(WebServiceStatus webServiceStatus) {
        return webServiceStatusRepository.save(webServiceStatus);
    }

    @Override
    public WebServiceStatus updateWebServiceStatus(Long id, WebServiceStatus webServiceStatus) {
        if (webServiceStatusRepository.existsById(id)) {
            webServiceStatus.setId(id);
            return webServiceStatusRepository.save(webServiceStatus);
        }
        return null;
    }

    @Override
    public void deleteWebServiceStatus(Long id) {
        webServiceStatusRepository.deleteById(id);
    }

    @Override
    public WebServiceStatus getTopWebServiceStatusByWebService(WebService webService) {
        Optional<WebServiceStatus> optionalWebServiceStatus = webServiceStatusRepository.findTopByWebServiceOrderByDownAtDesc(webService);
        return optionalWebServiceStatus.orElse(null);
    }

    @Override
    public List<WebServiceStatus> getWebServiceStatusByWebServiceAndStartTimeAndEndTime(
            WebService webService, Date startDate, Date endDate) {
        List<WebServiceStatus> webServiceStatuses = webServiceStatusRepository.findByWebServiceAndUpAtBetweenOrderByDownAt(webService, startDate, endDate);

        List<WebServiceStatus> webServiceUpAtNullStatuses = webServiceStatusRepository.findByWebServiceAndUpAtNullOrderByDownAt(webService);

        webServiceStatuses.addAll(webServiceUpAtNullStatuses);

        return webServiceStatuses;
    }

    @Override
    public WebServiceStatus getWebServiceStatusByWebService(WebService webService) {
        Optional<WebServiceStatus> optionalWebServiceStatus = webServiceStatusRepository.findByWebServiceAndUpAtNull(webService);
        return optionalWebServiceStatus.orElse(null);
    }
}
