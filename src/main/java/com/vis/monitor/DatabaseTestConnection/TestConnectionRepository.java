package com.vis.monitor.DatabaseTestConnection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestConnectionRepository extends JpaRepository<TestConnection, Long> {

	TestConnection save(TestConnection testConnection);
    
}
