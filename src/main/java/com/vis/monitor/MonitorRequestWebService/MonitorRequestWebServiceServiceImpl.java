package com.vis.monitor.MonitorRequestWebService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorRequestWebServiceServiceImpl implements MonitorRequestWebServiceService {

	
	@Autowired
	private MonitorRequestWebServiceRepository mrwRepo;
	@Override
	public MonitorRequestWebService addMonitorRequestWebService(MonitorRequestWebService mrsService) {
		// TODO Auto-generated method stub
		return mrwRepo.save(mrsService);
	}

	@Override
	public MonitorRequestWebService getMonitorRequestWebService(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorRequestWebService>mrsService=mrwRepo.findById(id);
		
		return mrsService.isPresent() ? mrsService.get() : null;

	}

	@Override
	public MonitorRequestWebService updateMonitorRequestWebService(MonitorRequestWebService mrsService) {
		// TODO Auto-generated method stub
		return mrwRepo.save(mrsService);
	}

	@Override
	public MonitorRequestWebService deleteMonitorRequestWebService(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorRequestWebService>mrsService=mrwRepo.findById(id);
		if(mrsService.isPresent()) {
			mrwRepo.deleteById(id);
		}
		return mrsService.isPresent() ? mrsService.get():null;
	}

	@Override
	public List<MonitorRequestWebService> getMonitorRequestWebServices() {
		// TODO Auto-generated method stub
		List<MonitorRequestWebService>findAll=mrwRepo.findAll();
		return findAll;
	}

	
}
