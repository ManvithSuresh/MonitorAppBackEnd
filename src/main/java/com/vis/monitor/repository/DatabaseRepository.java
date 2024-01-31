package com.vis.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vis.monitor.db.modal.Database;

@Repository
public interface DatabaseRepository extends JpaRepository<Database, Long> {

	Database findByName(String databaseName);

}
