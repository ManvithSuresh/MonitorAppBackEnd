package com.vis.monitor.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vis.monitor.db.modal.Database;
import com.vis.monitor.db.modal.DatabaseStatus;
import com.vis.monitor.modal.Server;
import com.vis.monitor.modal.ServerStatus;

@Repository
public interface DatabaseStatusRepository extends JpaRepository<DatabaseStatus, Long> {

	
	

	Optional<DatabaseStatus> findByDatabaseAndUpAtNull(Database database);

	Optional<DatabaseStatus> findTopByDatabaseOrderByDownAtDesc(Database database);




	List<DatabaseStatus> findByDatabaseAndUpAtNullOrderByDownAt(Database database);

	List<DatabaseStatus> findByDatabaseAndUpAtBetweenOrderByDownAt(Database database, Date startDate, Date endDate);

	
	
}