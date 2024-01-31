package com.vis.monitor.database.memory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DatabaseDashboardStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
private String variableName;

private String value;







	
}
