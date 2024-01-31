package com.vis.monitor.webservice.status;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.User;

@Service
public class MonitorWebServiceStatusServiceImpl implements MonitorWebServiceStatusService{

	@Autowired 
	private MonitorWebServiceStatusRepository mwebsRepo;
	
	@Override
	public List<MonitorWebServiceStatus> getMonitorWebServiceStatusesByUser(User user ){
		return mwebsRepo.findByUser(user);
	}
		@Override
		public MonitorWebServiceStatus addMonitorWebServiceStatus(MonitorWebServiceStatus monitorWebserviceStatus ) {
			return mwebsRepo.save(monitorWebserviceStatus);
			
		}
		
		
		@Override
		public MonitorWebServiceStatus updateMonitorWebServiceStatus(MonitorWebServiceStatus monitorWebServiceStatus) {
			return mwebsRepo.save(monitorWebServiceStatus);
		
	}
		
		@Override
		public List<MonitorWebServiceStatus> getMonitorWebServiceStatus(){
			return mwebsRepo.findAll();
			
		
		}
		
		@Override 
		public MonitorWebServiceStatus getMonitorWebServiceStatus(Long id) {
			Optional<MonitorWebServiceStatus> monitorWebServiceStatus=mwebsRepo.findById(id);
			return monitorWebServiceStatus.isPresent()? monitorWebServiceStatus.get():null;
			
			}
		
		@Override
		public MonitorWebServiceStatus deleteMonitorWebServiceStatus(Long id) {
			Optional<MonitorWebServiceStatus> monitorWebServiceStatus=mwebsRepo.findById(id);
			if(monitorWebServiceStatus.isPresent()) {
				mwebsRepo.deleteById(id);
				
			}
			return monitorWebServiceStatus.isPresent()?monitorWebServiceStatus.get():null;
		}
}


