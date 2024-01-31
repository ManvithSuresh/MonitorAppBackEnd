package com.vis.monitor.system.memory;

import lombok.Data;

@Data
public class Cpu {

	private double cpuUsage;
	
	private double averageCpuUsage;
	
	private double maxCpuUsage;
	
	private double minCpuUsage;
}
