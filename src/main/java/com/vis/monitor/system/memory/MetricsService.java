	package com.vis.monitor.system.memory;
	
	import java.util.LinkedList;
	import java.util.List;
import java.util.Map;
import java.util.Queue;
	import java.util.concurrent.ConcurrentLinkedQueue;
	
	public interface MetricsService {
	
//		static Queue<Metrics> queueMetricsInfo = new LinkedList<Metrics>();
		static final Queue<Metrics> queueMetricsInfo = new ConcurrentLinkedQueue<>();
	
		public static void addMetrics(Metrics metrics) {
			if (queueMetricsInfo.size() >= 60) {
				queueMetricsInfo.poll();
		   }
			queueMetricsInfo.add(metrics);
		}
		
		  public static int getQueueSize() {
		        return queueMetricsInfo.size();
		    }
		  
		  
		  
		public List<Cpu> getLast60SecondsUsageOfCPU();
		
		public List<Ram> getLast60SecondsUsageOfRam();
			

		 public List<Disk> getLastUsageOfDisk();
		
	}