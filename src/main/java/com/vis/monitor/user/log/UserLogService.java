package com.vis.monitor.user.log;

import java.util.List;

import com.vis.monitor.modal.User;

public interface UserLogService {

	    void saveUserLog(UserLog userLog);
	
	    List<UserLog> getUserLogsByUserId(Long userId);
	    List<UserLog> getAllUserLogs();

		UserLog findLatestLogin(User user);
	}


