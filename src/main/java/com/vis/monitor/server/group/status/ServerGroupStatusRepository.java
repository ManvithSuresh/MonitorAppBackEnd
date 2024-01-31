package com.vis.monitor.server.group.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vis.monitor.modal.ServerGroup;
@Repository
public interface ServerGroupStatusRepository extends JpaRepository<ServerGroupStatus, Long> {
Optional<ServerGroupStatus> findByServerGroupAndUpAtNull(ServerGroup server);
	
	Optional<ServerGroupStatus> findTopByServerGroupOrderByDownAtDesc(ServerGroup server);
	
	List<ServerGroupStatus> findByServerGroupAndUpAtBetweenOrderByDownAt(ServerGroup server, Date startDate, Date endDate);
	
	List<ServerGroupStatus> findByServerGroupAndUpAtNullOrderByDownAt(ServerGroup server);
}
