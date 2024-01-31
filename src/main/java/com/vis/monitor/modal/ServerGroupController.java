package com.vis.monitor.modal;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServerGroupController {

    @Autowired
    private ServerGroupService serverGroupService;
    

    
    
  

    @PostMapping("add-server-group")
    public ServerGroup saveServer(@RequestBody ServerGroup servergroup) {
        return serverGroupService.saveServerGroup(servergroup);
    }


    @GetMapping("get-server-group")
    public List<ServerGroup> getAllServerGroups() {
        return serverGroupService.getAllServerGroups();
    }

    @GetMapping("get-server-group-by/{id}")
    public ServerGroup getServerGroupById(@PathVariable Long id) {
        return serverGroupService.getServerGroupById(id);
    }

    @PostMapping("update-server-group")
    public ServerGroup saveServerGroup(@RequestBody ServerGroup serverGroup) {
        return serverGroupService.saveServerGroup(serverGroup);
    }

 
}
