package com.vis.monitor.server.group.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.ServerGroup;

@Service
public class ServerGroupStatusServiceImpl implements ServerGroupStatusService {

	@Autowired
	private ServerGroupStatusRepository sRepo;
	
	@Override
	public ServerGroupStatus addServerStatus(ServerGroupStatus serverStatus) {
		// TODO Auto-generated method stub
		return sRepo.save(serverStatus);
	}

	@Override
	public ServerGroupStatus updateServerStatus(ServerGroupStatus serverStatus) {
		// TODO Auto-generated method stub
		return sRepo.save(serverStatus);
	}

	@Override
	public List<ServerGroupStatus> getServerStatuses() {
		// TODO Auto-generated method stub
		return sRepo.findAll();
	}

	@Override
	public ServerGroupStatus getServerStatusById(Long id) {
		// TODO Auto-generated method stub
		Optional<ServerGroupStatus> oServer = sRepo.findById(id);
		return oServer.isPresent() ? oServer.get() : null;
	}
	
	@Override
	public ServerGroupStatus getServerStatusByServer(ServerGroup server) {
		// TODO Auto-generated method stub
		Optional<ServerGroupStatus> oServer = sRepo.findByServerGroupAndUpAtNull(server);
		return oServer.isPresent() ? oServer.get() : null;
	}
	
	@Override
	public ServerGroupStatus getTopServerStatusByServer(ServerGroup server) {
		// TODO Auto-generated method stub
		Optional<ServerGroupStatus> oServer = sRepo.findTopByServerGroupOrderByDownAtDesc(server);
		return oServer.isPresent() ? oServer.get() : null;
	}

	@Override
	public ServerGroupStatus deleteServerStatus(Long id) {
		// TODO Auto-generated method stub
		Optional<ServerGroupStatus> oServer = sRepo.findById(id);
		if(oServer.isPresent()) {
			sRepo.deleteById(id);
		}
		return oServer.isPresent() ? oServer.get() : null;
	}
	
	public List<ServerGroupStatus> getServerStatusByServerAndStartTimeAndEndTime(ServerGroup server, Date startDate, Date endDate){
		
		List<ServerGroupStatus> serverStatuses = sRepo.findByServerGroupAndUpAtBetweenOrderByDownAt(server, startDate, endDate);
		
		List<ServerGroupStatus> serverUpAtNullStatuses = sRepo.findByServerGroupAndUpAtNullOrderByDownAt(server);
		
		serverStatuses.addAll(serverUpAtNullStatuses);
		
		return serverStatuses; 
		
	}
	
}

