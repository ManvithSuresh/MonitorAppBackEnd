package com.vis.monitor.modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServerGroupServiceImpl implements ServerGroupService {

    @Autowired	
    private ServerGroupRepository serverGroupRepository;

    @Transactional
    @Override
    public List<ServerGroup> getAllServerGroups() {
        List<ServerGroup> serverGroups = serverGroupRepository.findAll();
        // Initialize the services collection for each entity
        serverGroups.forEach(serverGroup -> initializeServices(serverGroup));
        return serverGroups;
    }

    @Override
    public ServerGroup getServerGroupById(Long id) {
        ServerGroup serverGroup = serverGroupRepository.findById(id).orElse(null);
        // Initialize the services collection if the entity is found
        if (serverGroup != null) {
            initializeServices(serverGroup);
        }
        return serverGroup;
    }

    @Override
    public ServerGroup saveServerGroup(ServerGroup serverGroup) {
        return serverGroupRepository.save(serverGroup);
    }

    @Override
    public ServerGroup saveServer(ServerGroup serverGroup) {
        return serverGroupRepository.save(serverGroup);
    }

    // Helper method to initialize the services collection
    private void initializeServices(ServerGroup serverGroup) {
        if (serverGroup != null && serverGroup.getServices() != null) {
            serverGroup.getServices().size(); // Force initialization
        }
    }
}
