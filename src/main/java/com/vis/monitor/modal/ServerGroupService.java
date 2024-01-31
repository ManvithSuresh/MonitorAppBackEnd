package com.vis.monitor.modal;




import java.util.List;



public interface ServerGroupService {
    ServerGroup saveServerGroup(ServerGroup serverGroup);

    List<ServerGroup> getAllServerGroups();

    ServerGroup getServerGroupById(Long id);

	ServerGroup saveServer(ServerGroup servergroup);

   
}
