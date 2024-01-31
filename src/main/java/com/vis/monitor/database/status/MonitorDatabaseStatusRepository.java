package com.vis.monitor.database.status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vis.monitor.modal.MonitorStatus;
import com.vis.monitor.modal.User;

public interface MonitorDatabaseStatusRepository extends JpaRepository<MonitorDatabaseStatus, Long> {
	List<MonitorDatabaseStatus> findByUser(User user);
}
