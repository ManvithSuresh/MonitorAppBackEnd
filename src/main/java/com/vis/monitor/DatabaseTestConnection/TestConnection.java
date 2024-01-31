package com.vis.monitor.DatabaseTestConnection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TestConnection {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String url;
    private String databaseType;
    private String username;
    private String password;
    private boolean connectionResult;
}
