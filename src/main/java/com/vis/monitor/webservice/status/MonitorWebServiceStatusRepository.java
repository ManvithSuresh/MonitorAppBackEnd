package com.vis.monitor.webservice.status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vis.monitor.modal.User;

public interface MonitorWebServiceStatusRepository extends JpaRepository<MonitorWebServiceStatus,Long>{
List<MonitorWebServiceStatus> findByUser(User user);
}
