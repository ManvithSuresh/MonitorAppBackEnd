package com.vis.monitor.system.memory;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data

public class Disk {

	private String driveLetter;
	
	private double totalSpace;
	
	private double freeSpace;
	
	private double usedSpace;
	
}
