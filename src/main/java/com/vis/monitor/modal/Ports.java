package com.vis.monitor.modal;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Embeddable
@Data
public class Ports{
	

	
	private Integer ports;
	
	private String serviceName;
	
	private Boolean isActive = false;
}
