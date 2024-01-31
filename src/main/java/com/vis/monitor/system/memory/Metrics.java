	package com.vis.monitor.system.memory;
	
	import com.fasterxml.jackson.databind.annotation.JsonSerialize;
	
	import lombok.Data;
	
	@Data
	@JsonSerialize
	public class Metrics {
	
		private Cpu cpuUsage;
		
		private Ram ramUsage;
		
		private Disk diskUsage;
	}
