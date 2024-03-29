package com.vis.monitor.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.MonitorRequest;
import com.vis.monitor.modal.Server;
import com.vis.monitor.modal.User;
import com.vis.monitor.repository.MonitorRequestRepository;

import com.vis.monitor.service.MonitorRequestService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MonitorRequestServiceImpl implements MonitorRequestService {

	@Autowired
	private MonitorRequestRepository mrRepo;
	
	@Override
	public MonitorRequest addMonitorRequest(MonitorRequest monitorRequest) {
		// TODO Auto-generated method stub
		return mrRepo.save(monitorRequest);
	}

	@Override
	public MonitorRequest updateMonitorRequest(MonitorRequest monitorRequest) {
		// TODO Auto-generated method stub
		return mrRepo.save(monitorRequest);
	}

	@Override
	@Transactional
	public List<MonitorRequest> getMonitorRequests() {
		// TODO Auto-generated method stub
		List<MonitorRequest> findAll = mrRepo.findAll();
		log.info("Scheduled task monitorRequest."+ findAll);

		return findAll;
	}

	@Override
	public MonitorRequest getMonitorRequest(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorRequest> oMonitorRequest = mrRepo.findById(id);
		return oMonitorRequest.isPresent() ?  oMonitorRequest.get() : null;
	}

	@Override
	public MonitorRequest deleteMonitorRequest(Long id) {
		// TODO Auto-generated method stub
		Optional<MonitorRequest> oMonitorRequest = mrRepo.findById(id);
		if(oMonitorRequest.isPresent()) 
		{
			mrRepo.deleteById(id);
		}
		return oMonitorRequest.isPresent() ? oMonitorRequest.get() : null; 
	}


	}
	

