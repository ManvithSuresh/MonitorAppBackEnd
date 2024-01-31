package com.vis.monitor.MonitorRequestDatabase;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
@Repository
public interface MonitorRequestDatabaseRepository extends JpaRepository<MonitorRequestDatabase, Long>{
}
