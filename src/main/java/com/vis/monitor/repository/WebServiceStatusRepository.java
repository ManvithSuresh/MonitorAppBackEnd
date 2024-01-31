package com.vis.monitor.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vis.monitor.ws.modal.WebService;
import com.vis.monitor.ws.modal.WebServiceStatus;



public interface WebServiceStatusRepository extends JpaRepository<WebServiceStatus, Long> {

    Optional<WebServiceStatus> findByWebServiceAndUpAtNull(WebService webService);

    Optional<WebServiceStatus> findTopByWebServiceOrderByDownAtDesc(WebService webService);

    List<WebServiceStatus> findByWebServiceAndUpAtNullOrderByDownAt(WebService webService);

    List<WebServiceStatus> findByWebServiceAndUpAtBetweenOrderByDownAt(
            WebService webService, Date startDate, Date endDate);
}