package com.vis.monitor.service;

import java.util.List;

import com.vis.monitor.modal.Server;

public interface ServerService {

	public Server  addServers(Server server);

	public Server  updateServers(Server server);
	
	public List<Server> getServers();
	
	public Server getServer(Long id);
	
	public Server deleteServer(Long id);
	
	
}



