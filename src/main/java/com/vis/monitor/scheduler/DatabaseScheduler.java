////
////package com.vis.monitor.scheduler;
////
////import java.sql.Connection;
////
////import java.sql.SQLException;
////import java.util.Date;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////
////import java.util.concurrent.ScheduledExecutorService;
////import java.util.concurrent.TimeUnit;
////
////import javax.annotation.PostConstruct;
////import javax.sql.DataSource;
////import javax.transaction.Transactional;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.scheduling.annotation.Scheduled;
////import org.springframework.stereotype.Component;
////
////import com.vis.monitor.MonitorRequestDatabase.MonitorRequestDatabase;
////import com.vis.monitor.MonitorRequestDatabase.MonitorRequestDatabaseService;
////import com.vis.monitor.database.status.MonitorDatabaseStatus;
////import com.vis.monitor.database.status.MonitorDatabaseStatusService;
////import com.vis.monitor.db.modal.Database;
////import com.vis.monitor.db.modal.DatabaseStatus;
////import com.vis.monitor.modal.MonitorRequest;
////import com.vis.monitor.modal.MonitorStatus;
////import com.vis.monitor.modal.User;
////import com.vis.monitor.service.DatabaseStatusService;
////import com.vis.monitor.service.EmailService;
////import com.vis.monitor.service.MonitorRequestService;
////import com.vis.monitor.service.MonitorStatusService;
////import com.vis.monitor.utils.AppConstants;
////import com.zaxxer.hikari.HikariConfig;
////import com.zaxxer.hikari.HikariDataSource;
////import java.util.concurrent.Executors;
////
////import lombok.extern.log4j.Log4j2;
////
////	@Component
////	@Log4j2
////	@Transactional
////	public class DatabaseScheduler{
////		
////		 @Autowired
////			private DatabaseStatusService dbStatusService; 
////	
////			@Autowired
////			private EmailService emailService;	
////			@Autowired
////			private MonitorRequestDatabaseService mrService;
////	
////			@Autowired
////			private MonitorDatabaseStatusService msService;
////			
////			
////			private Map<Database, Boolean> prvDatabaseSMap = new HashMap<>();
////			
////			
////			 private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
////
////			    @PostConstruct
////			    public void initializeScheduler() {
////			        scheduler.scheduleAtFixedRate(this::checkDatabaseAndSendEmails, 0, 50, TimeUnit.SECONDS);
////			    }
////			
////			    @Scheduled(fixedDelay = 10000)
////			    public void checkDatabaseAndSendEmails() {
////			        log.info("Scheduled task to monitor database started.");
////
////			        List<MonitorRequestDatabase> monitorDatabaseRequests = mrService.getMonitorRequestDatabases();
////			        log.info("Total requests: " + monitorDatabaseRequests.size());
////
////			        for (MonitorRequestDatabase monitorDatabaseRequest : monitorDatabaseRequests) {
////			            Database database = monitorDatabaseRequest.getDatabase();
////			            log.info("Databases: " + monitorDatabaseRequests);
////
////			            boolean isDatabaseNotReachable = checkingDatabase(database);
////			            log.warn("Database is null for monitorRequest: " + monitorDatabaseRequest);
////
////			            boolean wasDatabaseReachable = false;
////			            log.info("Database {} - Current Reachable: {}, Was Reachable: {}", database, isDatabaseNotReachable, wasDatabaseReachable);
////
////			            if (prvDatabaseSMap.containsKey(database)) {
////			                wasDatabaseReachable = prvDatabaseSMap.get(database);
////			                log.info("The status is: " + wasDatabaseReachable);
////			            } else {
////			                DatabaseStatus ddbStatusService = dbStatusService.getTopDatabaseStatusByDatabase(database);
////			                wasDatabaseReachable = (ddbStatusService != null && ddbStatusService.getUpAt() != null);
////			                log.info("The message is: " + wasDatabaseReachable);
////			            }
////
////			            database.setIsActive(!isDatabaseNotReachable);
////
////			            if (!isDatabaseNotReachable || wasDatabaseReachable) {
////			                log.warn("Database {} is not reachable URL: {}", database.getUrl());
////
////			                DatabaseStatus ddDatabaseStatus = dbStatusService.getDatabaseStatusByDatabase(database);
////
////			                if (ddDatabaseStatus == null) {
////			                    ddDatabaseStatus = new DatabaseStatus();
////			                    ddDatabaseStatus.setDatabase(database);
////			                    ddDatabaseStatus.setDownAt(new Date());
////			                    dbStatusService.addDatabaseStatus(ddDatabaseStatus);
////			                }
////
////			                List<User> users = monitorDatabaseRequest.getUsers();
////			                log.info("Total users: " + users);
////			                if (users != null) {
////			                    for (User user : users) {
////			                        sendDatabaseNotReachableEmail(user, database);
////			                    }
////			                } else {
////			                    log.warn("No user details found for database {}", database.getName());
////			                }
////			            } else if (isDatabaseNotReachable || !wasDatabaseReachable) {
////			                log.info("Database {} is reachable again. URL: {}", database.getName(), database.getUrl());
////
////			                DatabaseStatus ddDatabaseStatus = dbStatusService.getDatabaseStatusByDatabase(database);
////
////			                if (ddDatabaseStatus != null && ddDatabaseStatus.getUpAt() == null) {
////			                    Date upAt = new Date();
////			                    ddDatabaseStatus.setUpAt(upAt);
////
////			                    long diffInMillies = Math.abs(upAt.getTime() - ddDatabaseStatus.getDownAt().getTime());
////			                    long duration = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
////			                    ddDatabaseStatus.setDuration(duration);
////
////			                    if (ddDatabaseStatus.getId() != null) {
////			                        dbStatusService.updateDatabaseStatus(ddDatabaseStatus.getId(), ddDatabaseStatus);
////			                    } else {
////			                        log.error("ID is null while updating DatabaseStatus");
////			                    }
////			                }
////
////			                List<User> users = monitorDatabaseRequest.getUsers();
////			                if (users != null) {
////			                    for (User user : users) {
////			                        sendDatabaseReachableEmail(user, database);
////			                    }
////			                } else {
////			                    log.warn("No user details found for database {}", database.getName());
////			                }
////			            }
////
////			            prvDatabaseSMap.put(database, isDatabaseNotReachable);
////			        }
////
////			        log.info("Scheduled task to monitor database completed.");
////			    }
////	
////			
////		private boolean checkingDatabase(Database database) {
////			log.info("calling checking database method");
////			
////			if (database != null) {
////				DataSource dataSource=createDataSource(database);
////				return checkingDatabase(dataSource);
////			}
////			return false;
////		}
////	
////	private boolean checkingDatabase(DataSource dataSource) {
////		try {
////			Connection connection=dataSource.getConnection();
////			log.info("Successfully connected to the database.");
////			boolean isReachable =     connection.createStatement().execute("SELECT 1");
////	         
////	         
////	         log.info("Database is reachable: {}", isReachable);
////
////	         ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
////	         scheduler.schedule(() -> {
////	             // Close the connection pool
////				 ((HikariDataSource) dataSource).close();
////				 log.info("Connection pool closed after 2 seconds.");
////	         }, 2, TimeUnit.SECONDS);
////	         
////	         
////			return true;
////		} catch (SQLException e) {
////			 log.error("Unable to make connection with DB", e);
////		}
////		return false;
////	}
////	
////	
////	private DataSource createDataSource(Database database) {
////	    String type = database.getType();
////	    String url = database.getUrl();
////	    String username = database.getUserName();
////	    String password = database.getPassword();
////	    
////	  
////	    HikariConfig config = new HikariConfig();
////	    config.setJdbcUrl(url);
////	    config.setUsername(username);
////	    config.setPassword(password);
////
////	    if ("mysql".equalsIgnoreCase(type)) {
////	        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
////	       
////	    } else if ("postgresql".equalsIgnoreCase(type)) {
////	        config.setDriverClassName("org.postgresql.Driver");
////	        
////	    } else if ("mssql".equalsIgnoreCase(type)) {
////	        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
////	      
////	    } else if ("mongodb".equalsIgnoreCase(type)) {
////	      
////	    } else if ("mariadb".equalsIgnoreCase(type)) {
////	       
////	    }
////	    
////	    config.setMaximumPoolSize(10);
////	    config.setConnectionTimeout(30000);
////	    config.setIdleTimeout(600000);
////	    
////	    return new HikariDataSource(config);
////	}
////		  
////		  private void sendDatabaseNotReachableEmail(User user, Database database) {
////
////			    List<MonitorDatabaseStatus> monitorStatuses = msService.getMonitorDatabaseStatusesByUser(user);
////
////			    boolean isSent = false;
////
////			    for (MonitorDatabaseStatus monitorStatus : monitorStatuses) {
////			        if (monitorStatus.getIsSent()) {
////			            isSent = true;
////			            log.info("Email already sent to {} for unreacable databases", user);
////			            break;
////			        }
////			    }
////
////			    if (!isSent) {
////			        MonitorDatabaseStatus monitorDatabaseStatus = new MonitorDatabaseStatus();
////
////			        try {
////			            String subject = "Database Not Reachable Alert";
////			            String message = "The URL " + database.getUrl() + " is not reachable.";
////
////			            isSent = emailService.sendEmail(user.getEMail(), subject, message);
////
////			            monitorDatabaseStatus.setUser(user);
////			            monitorDatabaseStatus.setIsSent(isSent);
////			            monitorDatabaseStatus.setSentAt(new Date());
////			            monitorDatabaseStatus.setDatabase(database);
////
////			            log.info("Email notification sent to {} successfully.", user);
////			        } catch (Exception ex) {
////			            log.error("Failed to send email notification to {} for unreachable database: {}", user, ex.getMessage());
////			        }
////
////			        msService.addMonitorDatabaseStatus(monitorDatabaseStatus);
////			    }
////			}
////
////			private void sendDatabaseReachableEmail(User user, Database database) {
////			    List<MonitorDatabaseStatus> monitorDatabaseStatuses = msService.getMonitorDatabaseStatusesByUser(user);
////
////			    boolean isSent = false;
////
////			    for (MonitorDatabaseStatus monitorStatus : monitorDatabaseStatuses) {
////			        if (monitorStatus.getIsSent()) {
////			            isSent = true;
////			            log.info("Email already sent to {} for reachable database.", user);
////			            break;
////			        }
////			    }
////
////			    if (!isSent) {
////			        MonitorDatabaseStatus monitorStatus = new MonitorDatabaseStatus();
////
////			        try {
////			            String subject = "Database Reachable Alert";
////			            String message = "The URL " + database.getUrl() + " is reachable again.";
////
////			            isSent = emailService.sendEmail(user.getEMail(), subject, message);
////
////			            monitorStatus.setUser(user);
////			            monitorStatus.setIsSent(isSent);
////			            monitorStatus.setSentAt(new Date());
////			            monitorStatus.setDatabase(database);
////
////			            log.info("Email notification sent to {} successfully.", user);
////			        } catch (Exception ex) {
////			            log.error("Failed to send email notification to {} for reachable database: {}", user, ex.getMessage());
////			        }
////
////			        msService.addMonitorDatabaseStatus(monitorStatus);
////			    }
////			}
////	}
//
//package com.vis.monitor.scheduler;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.vis.monitor.MonitorRequestDatabase.MonitorRequestDatabase;
//import com.vis.monitor.MonitorRequestDatabase.MonitorRequestDatabaseService;
//import com.vis.monitor.database.status.MonitorDatabaseStatus;
//import com.vis.monitor.database.status.MonitorDatabaseStatusService;
//import com.vis.monitor.db.modal.Database;
//import com.vis.monitor.db.modal.DatabaseStatus;
//import com.vis.monitor.modal.User;
//import com.vis.monitor.service.DatabaseStatusService;
//import com.vis.monitor.service.EmailService;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import lombok.extern.log4j.Log4j2;
//
//@Component
//@Log4j2
//@Transactional
//public class DatabaseScheduler {
//
//    private final DatabaseStatusService dbStatusService;
//    private final EmailService emailService;
//    private final MonitorRequestDatabaseService mrService;
//    private final MonitorDatabaseStatusService msService;
//
//    private Map<Database, Boolean> prvDatabaseSMap = new HashMap<>();
//
//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//    @Autowired
//    public DatabaseScheduler(DatabaseStatusService dbStatusService, EmailService emailService,
//            MonitorRequestDatabaseService mrService, MonitorDatabaseStatusService msService) {
//        this.dbStatusService = dbStatusService;
//        this.emailService = emailService;
//        this.mrService = mrService;
//        this.msService = msService;
//    }
//
//    @PostConstruct
//    public void initializeScheduler() {
//        scheduler.scheduleAtFixedRate(this::checkDatabaseAndSendEmails, 0, 50, TimeUnit.SECONDS);
//    }
//
//    @Scheduled(fixedDelay = 10000)
//    public void checkDatabaseAndSendEmails() {
//        log.info("Scheduled task to monitor database started.");
//
//        List<MonitorRequestDatabase> monitorDatabaseRequests = mrService.getMonitorRequestDatabases();
//        log.info("Total requests: {}", monitorDatabaseRequests.size());
//
//        if (monitorDatabaseRequests != null && !monitorDatabaseRequests.isEmpty()) {
//            log.info("Total requests: {}", monitorDatabaseRequests.size());
//
//            for (MonitorRequestDatabase monitorDatabaseRequest : monitorDatabaseRequests) {
//                Database database = monitorDatabaseRequest.getDatabase();
//                if (database == null) {
//                    log.warn("Database is null for monitorRequest: {}", monitorDatabaseRequest);
//                    continue; // Skip to the next iteration
//                }
//
//                log.info("Database: {}", database);
//
//                boolean isDatabaseNotReachable = checkingDatabase(database);
//                log.info("Database {} - Reachable: {}", database.getName(), !isDatabaseNotReachable);
//
//                database.setIsActive(!isDatabaseNotReachable);
//                log.info("Setting isActive to: {}", database.getIsActive());
//
//                boolean wasDatabaseReachable = prvDatabaseSMap.getOrDefault(database, false);
//
//                if (!wasDatabaseReachable) {
//                    DatabaseStatus ddbStatusService = dbStatusService.getTopDatabaseStatusByDatabase(database);
//                    wasDatabaseReachable = (ddbStatusService != null && ddbStatusService.getUpAt() != null);
//                    log.info("Was Database Reachable: {}", wasDatabaseReachable);
//                }
//
//                if (isDatabaseNotReachable && !wasDatabaseReachable) {
//                    log.warn("Database {} is not reachable URL: {}", database.getName(), database.getUrl());
//
//                    DatabaseStatus ddDatabaseStatus = dbStatusService.getDatabaseStatusByDatabase(database);
//
//                    if (ddDatabaseStatus == null) {
//                        ddDatabaseStatus = new DatabaseStatus();
//                        ddDatabaseStatus.setDatabase(database);
//                        ddDatabaseStatus.setDownAt(new Date());
//                        dbStatusService.addDatabaseStatus(ddDatabaseStatus);
//                    }
//
//                    List<User> users = monitorDatabaseRequest.getUsers();
//                    log.info("Total users: {}", users);
//                    if (users != null) {
//                        for (User user : users) {
//                            sendDatabaseNotReachableEmail(user, database);
//                        }
//                    } else {
//                        log.warn("No user details found for database {}", database.getName());
//                    }
//                } else if (!isDatabaseNotReachable && wasDatabaseReachable) {
//                    log.info("Database {} is reachable again. URL: {}", database.getName(), database.getUrl());
//
//                    DatabaseStatus ddDatabaseStatus = dbStatusService.getDatabaseStatusByDatabase(database);
//
//                    if (ddDatabaseStatus != null && ddDatabaseStatus.getUpAt() == null) {
//                        handleDatabaseReachable(ddDatabaseStatus);
//                    }
//
//                    List<User> users = monitorDatabaseRequest.getUsers();
//                    if (users != null) {
//                        for (User user : users) {
//                            sendDatabaseReachableEmail(user, database);
//                        }
//                    } else {
//                        log.warn("No user details found for database {}", database.getName());
//                    }
//                }
//
//                prvDatabaseSMap.put(database, isDatabaseNotReachable);
//            }
//
//            log.info("Scheduled task to monitor database completed.");
//        } else {
//            log.info("No monitor database requests found. Skipping monitoring.");
//        }
//    }
//    private void handleDatabaseReachable(DatabaseStatus ddDatabaseStatus) {
//        Date upAt = new Date();
//        ddDatabaseStatus.setUpAt(upAt);
//
//        long diffInMillies = Math.abs(upAt.getTime() - ddDatabaseStatus.getDownAt().getTime());
//        long duration = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//        ddDatabaseStatus.setDuration(duration);
//
//        if (ddDatabaseStatus.getId() != null) {
//            dbStatusService.updateDatabaseStatus(ddDatabaseStatus.getId(), ddDatabaseStatus);
//        } else {
//            log.error("ID is null while updating DatabaseStatus");
//        }
//    }
//
//    private boolean checkingDatabase(Database database) {
//        log.info("Checking database method");
//
//        if (database != null) {
//            DataSource dataSource = createDataSource(database);
//            return checkingDatabase(dataSource, database);
//        }
//
//        log.warn("Database is null. Cannot check reachability.");
//        return false;
//    }
//
//    private boolean checkingDatabase(DataSource dataSource, Database database) {
//        try (Connection connection = dataSource.getConnection()) {
//            log.info("Successfully connected to the database.");
//            boolean isReachable = connection.createStatement().execute("SELECT 1");
//
//            log.info("Database {} - Reachable: {}", database.getName(), isReachable);
//
//            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
//                // Close the connection pool
//                ((HikariDataSource) dataSource).close();
//                log.info("Connection pool closed after 2 seconds.");
//            }, 2, TimeUnit.SECONDS);
//
//            return isReachable;
//        } catch (SQLException e) {
//            log.error("Unable to make connection with DB", e);
//        }
//
//        log.warn("Could not check database reachability. Assuming not reachable.");
//        return false;
//    }
//
//    private DataSource createDataSource(Database database) {
//        String type = database.getType();
//        String url = database.getUrl();
//        String username = database.getUserName();
//        String password = database.getPassword();
//
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(username);
//        config.setPassword(password);
//
//        if ("mysql".equalsIgnoreCase(type)) {
//            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        } else if ("postgresql".equalsIgnoreCase(type)) {
//            config.setDriverClassName("org.postgresql.Driver");
//        } else if ("mssql".equalsIgnoreCase(type)) {
//            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        } else if ("mongodb".equalsIgnoreCase(type)) {
//            // Handle MongoDB configuration if needed
//        } else if ("mariadb".equalsIgnoreCase(type)) {
//            // Handle MariaDB configuration if needed
//        }
//
//        config.setMaximumPoolSize(10);
//        config.setConnectionTimeout(30000);
//        config.setIdleTimeout(600000);
//
//        return new HikariDataSource(config);
//    }
//
//    private void sendDatabaseNotReachableEmail(User user, Database database) {
//        List<MonitorDatabaseStatus> monitorStatuses = msService.getMonitorDatabaseStatusesByUser(user);
//
//        if (monitorStatuses.stream().anyMatch(MonitorDatabaseStatus::getIsSent)) {
//            log.info("Email already sent to {} for unreachable databases", user);
//            return;
//        }
//
//        MonitorDatabaseStatus monitorDatabaseStatus = new MonitorDatabaseStatus();
//
//        try {
//            String subject = "Database Not Reachable Alert";
//            String message = "The URL " + database.getUrl() + " is not reachable.";
//
//            boolean isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//            monitorDatabaseStatus.setUser(user);
//            monitorDatabaseStatus.setIsSent(isSent);
//            monitorDatabaseStatus.setSentAt(new Date());
//            monitorDatabaseStatus.setDatabase(database);
//
//            log.info("Email notification sent to {} successfully.", user);
//        } catch (Exception ex) {
//            log.error("Failed to send email notification to {} for unreachable database: {}", user,
//                    ex.getMessage());
//        }
//
//        msService.addMonitorDatabaseStatus(monitorDatabaseStatus);
//    }
//
//    private void sendDatabaseReachableEmail(User user, Database database) {
//        List<MonitorDatabaseStatus> monitorDatabaseStatuses = msService.getMonitorDatabaseStatusesByUser(user);
//
//        if (monitorDatabaseStatuses.stream().anyMatch(MonitorDatabaseStatus::getIsSent)) {
//            log.info("Email already sent to {} for reachable database.", user);
//            return;
//        }
//
//        MonitorDatabaseStatus monitorStatus = new MonitorDatabaseStatus();
//
//        try {
//            String subject = "Database Reachable Alert";
//            String message = "The URL " + database.getUrl() + " is reachable again.";
//
//            boolean isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//            monitorStatus.setUser(user);
//            monitorStatus.setIsSent(isSent);
//            monitorStatus.setSentAt(new Date());
//            monitorStatus.setDatabase(database);
//
//            log.info("Email notification sent to {} successfully.", user);
//        } catch (Exception ex) {
//            log.error("Failed to send email notification to {} for reachable database: {}", user,
//                    ex.getMessage());
//        }	
//
//        msService.addMonitorDatabaseStatus(monitorStatus);
//    }
//}
