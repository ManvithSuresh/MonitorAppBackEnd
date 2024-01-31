package com.vis.monitor.user.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    @PostMapping("/retrieve")
    public void createUserLog(@RequestBody UserLog userLog) {
        userLogService.saveUserLog(userLog);
   
    }

    @GetMapping("get-user-log/{userId}")
    public List<UserLog> getUserLogs(@PathVariable Long userId) {
        // Retrieve user logs based on user ID
        return userLogService.getUserLogsByUserId(userId);
    }

    @GetMapping("/get-all-user-logs")
    public List<UserLog> getAllUserLogs() {
        // Retrieve all user logs
        return userLogService.getAllUserLogs();
    }
}

