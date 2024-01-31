package com.vis.monitor.server.group.monitor.status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vis.monitor.modal.User;

@Repository
public interface MonitorServerGroupStatusRepository extends JpaRepository<MonitorServerGroupStatus, Long> {
	List<MonitorServerGroupStatus> findByUser(User user);
}
