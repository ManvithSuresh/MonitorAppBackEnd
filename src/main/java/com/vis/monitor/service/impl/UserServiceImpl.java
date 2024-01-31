package com.vis.monitor.service.impl;

import com.vis.monitor.dto.LoginDTO;
import com.vis.monitor.modal.User;
import com.vis.monitor.repository.UserRepository;
import com.vis.monitor.service.UserService;
import com.vis.monitor.user.log.UserLog;
import com.vis.monitor.user.log.UserLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.time.Duration;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository uRepo;
	 @Autowired
	    private UserLogService userLogService; 
	 
	  @Autowired
	    private UserRepository userRepository;
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return uRepo.save(user);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return uRepo.save(user);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return uRepo.findAll();
	}

	@Override
	public User getUser(Long id) {
		// TODO Auto-generated method stub
		Optional<User> oUser = uRepo.findById(id);
		return oUser.isPresent() ?  oUser.get() : null;
	}

	@Override
	public User deleteUser(Long id) {
		// TODO Auto-generated method stub
		Optional<User> oUser = uRepo.findById(id);
		if(oUser.isPresent()) 
		{
			uRepo.deleteById(id);
		}
		return oUser.isPresent() ? oUser.get() : null; 
	}

//	@Override
//	public User loginUser(LoginDTO login) {
//		// TODO Auto-generated method stub
//		
//		String eMailOrName = login.getUsername();
//		String password = login.getPassword();
//		
//		Optional<User> oUser = uRepo.findByeMailAndPassword(eMailOrName, password);
//		
//		if(oUser == null || !oUser.isPresent()) {
//			oUser = uRepo.findByNameAndPassword(eMailOrName, password);
//		}
//		
//		return oUser.isPresent() ? oUser.get() : null;
//	}

//	 @Override
//	    public User loginUser(LoginDTO login) {
//	        String eMailOrName = login.getUsername();
//	        String password = login.getPassword();
//
//	        Optional<User> oUser = uRepo.findByeMailAndPassword(eMailOrName, password);
//
//	        if (!oUser.isPresent()) {
//	            oUser = uRepo.findByNameAndPassword(eMailOrName, password);
//	        }
//
//	        oUser.ifPresent(user -> {
//	            // Create a new UserLog entry for login with the login time
//	            UserLog userLog = new UserLog();
//	            userLog.setUser(user);
//	            userLog.setLoginTime(LocalDateTime.now());	         
//	            userLog.setAction("login"); 
//
//	            // Set other fields as needed
//	            userLog.setLogoutTime(LocalDateTime.now());
//	            userLog.setLastLogin(LocalDateTime.now()); // Set last login time
//	            userLog.setUsageDuration(0); // Set initial usage duration
//	            Duration usageDuration = Duration.between(userLog.getLoginTime(), userLog.getLogoutTime());
//	            userLog.setUsageDuration(usageDuration.getSeconds());
//	            userLogService.saveUserLog(userLog);
//	        });
//
//	        return oUser.orElse(null);
//	    }
//	}
	
	
	@Override
	public User loginUser(LoginDTO login) {
	    String eMailOrName = login.getUsername();
	    String password = login.getPassword();

	    Optional<User> oUser = uRepo.findByeMailAndPassword(eMailOrName, password);

	    if (!oUser.isPresent()) {
	        oUser = uRepo.findByNameAndPassword(eMailOrName, password);
	    }

	    oUser.ifPresent(user -> {
	        // Create a new UserLog entry for login with the login time
	        UserLog userLog = new UserLog();
	        userLog.setUser(user);
	        userLog.setLoginTime(LocalDateTime.now());
	        userLog.setAction("login");

	        // Set other fields as needed
	        userLogService.saveUserLog(userLog);
	    });

	    return oUser.orElse(null);
	}

	// Method to handle logout
	public void logoutUser(User user) {
	    // Find the user log entry for the latest login
	    UserLog latestLogin = userLogService.findLatestLogin(user);

	    if (latestLogin != null) {
	        // Check if the user is not already logged out
	        if (latestLogin.getLogoutTime() == null) {
	            // Update the logout time for the latest login
	            latestLogin.setLogoutTime(LocalDateTime.now());
	            Duration usageDuration = Duration.between(latestLogin.getLoginTime(), latestLogin.getLogoutTime());
	            latestLogin.setUsageDuration(usageDuration.getSeconds());

	            // Save the updated user log entry
	            userLogService.saveUserLog(latestLogin);
	        }
	    }
	}
}