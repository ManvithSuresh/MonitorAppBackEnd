package com.vis.monitor.user.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.User;

@Service
public class UserLogServiceImpl implements UserLogService {
	
    @Autowired
    private UserLogRepository userLogRepository;

    @Override
    public void saveUserLog(UserLog userLog) {
        userLogRepository.save(userLog);
    }
    @Override
    public List<UserLog> getUserLogsByUserId(Long userId) {
        return userLogRepository.findByUserId(userId);
    }
    
    public UserLog findLatestLogin(User user) {
        List<UserLog> userLogs = userLogRepository.findTopByUserAndActionOrderByLoginTimeDesc(user, "login");
        
        if (userLogs.isEmpty()) {
            System.out.println("No login entry found for user: " + user.getName());
            return null;
        }

        System.out.println("Latest login time for user " + user.getName() + ": " + userLogs.get(0).getLoginTime());

        return userLogs.get(0);
    }

    @Override
    public List<UserLog> getAllUserLogs() {
        return userLogRepository.findAll();
    }

}

