//package com.vis.monitor.system.memory.utils;
//
//import java.lang.management.ManagementFactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.sun.management.OperatingSystemMXBean;
//import com.vis.monitor.system.memory.Metrics;
//import com.vis.monitor.system.memory.MetricsService;
//import com.vis.monitor.system.memory.Ram;
//
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//public class RamUtils {
//	


   


//	public static double checkRamUsage() {
//		// TODO Auto-generated method stub
//   
//		Ram ramInfo = new Ram();
//   // Check if the platform supports the OpearatingSystemMXBean interface
//   if (ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean) {
//       // Cast the MXBean to the specific interface
//       OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//		
//      long totalRam = osBean.getTotalPhysicalMemorySize();
//      log.info("Total RAM :" + formatBytes(totalRam));
//      ramInfo.setTotalRam(totalRam);
//      
//      long freeRam = osBean.getFreePhysicalMemorySize();
//      log.info("Free RAM: " + formatBytes(freeRam));
//      ramInfo.setFreeRam(freeRam);
//      
//      long usedRam = totalRam - freeRam;
//      log.info("Used Ram : " + formatBytes(usedRam));
//      ramInfo.setUsedRam(usedRam);
//
//      Metrics metricInfo = new Metrics();
//      metricInfo.setRamUsage(ramInfo);
//      MetricsService.addMetrics(metricInfo);
//   
//     
//   
//      return ((totalRam - freeRam) / (double) totalRam) * 100.0;
//
//         
//   } else {
//	   log.info("OperatingSystemMXBean is not supported on this platform.");
//	   return 0.0;
//   }
//   }
//   
//   private static String formatBytes(long bytes) {
//       int unit = 1024;
//       if (bytes < unit) return bytes + " B";
//       int exp = (int) (Math.log(bytes) / Math.log(unit));
//       String pre = "KMGTPE".charAt(exp - 1) + "";
//       return String.format("%.2f %sB", bytes / Math.pow(unit, exp), pre);
//   }
//	}

	package com.vis.monitor.system.memory.utils;

	import oshi.SystemInfo;
	import oshi.hardware.GlobalMemory;
	import oshi.util.FormatUtil;

	import com.vis.monitor.system.memory.Metrics;
	import com.vis.monitor.system.memory.MetricsService;
	import com.vis.monitor.system.memory.Ram;

	import lombok.extern.log4j.Log4j2;

	@Log4j2
	public class RamUtils {
		
		
		public static void main(String[] args) {
			checkRamUsage();
		}

	    public static double checkRamUsage() {
	        SystemInfo systemInfo = new SystemInfo();
	        GlobalMemory globalMemory = systemInfo.getHardware().getMemory();

	        Ram ramInfo = new Ram();

	        long totalRam = globalMemory.getTotal();
	        log.info("Total RAM: " + FormatUtil.formatBytes(totalRam));
	        ramInfo.setTotalRam(totalRam);

	        long freeRam = globalMemory.getAvailable();
	        log.info("Free RAM: " + FormatUtil.formatBytes(freeRam));
	        ramInfo.setFreeRam(freeRam);

	        long usedRam = totalRam - freeRam;
	        log.info("Used RAM: " + FormatUtil.formatBytes(usedRam));
	        ramInfo.setUsedRam(usedRam);

	        Metrics metricInfo = new Metrics();
	        metricInfo.setRamUsage(ramInfo);
	        MetricsService.addMetrics(metricInfo);

	        return (usedRam / (double) totalRam) * 100.0;
	    }
	}
