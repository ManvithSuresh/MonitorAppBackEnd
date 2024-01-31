package com.vis.monitor.user.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vis.monitor.modal.User;

public interface UserLogRepository extends JpaRepository<UserLog, Long>{
    List<UserLog> findByUserId(Long userId);

	List<UserLog> findTopByUserAndActionOrderByLoginTimeDesc(User user, String string);
}
