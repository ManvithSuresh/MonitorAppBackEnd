package com.vis.monitor.user.log;


import java.time.LocalDateTime;
import javax.persistence.*;

import com.vis.monitor.modal.User;

import lombok.Data;

@Data
@Entity
@Table(name = "user_logs")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;


    private LocalDateTime lastLogin;

    private long usageDuration;
    
    private String action;
}
