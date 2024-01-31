//package com.vis.monitor.scheduler;
//
//import java.io.IOException;
//import java.net.ConnectException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.vis.monitor.modal.MonitorRequest;
//import com.vis.monitor.modal.MonitorStatus;
//import com.vis.monitor.modal.Ports;
//import com.vis.monitor.modal.Server;
//import com.vis.monitor.modal.User;
//import com.vis.monitor.service.EmailService;
//import com.vis.monitor.service.MonitorRequestService;
//import com.vis.monitor.service.MonitorStatusService;
//import com.vis.monitor.service.ServerStatusService;
//
//import lombok.extern.log4j.Log4j2;
//
//@Component
//@Log4j2
//@Transactional
//public class ServerScheduler {	
//
//    @Autowired
//    private MonitorRequestService mrService;
//
//    @Autowired
//    private MonitorStatusService msService;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private ServerStatusService ssService;
//
//    private Map<Server, Boolean> prvState = new HashMap<>();
//
//    @Scheduled(fixedDelay = 100000)
//    public void checkIpPortsAndSendEmails() {
//        log.info("Scheduled task to monitor IP and Port started.");
//
//        List<MonitorRequest> monitorRequests = mrService.getMonitorRequests();
//        log.info("Total requests: {}", monitorRequests.size());
//
//        for (MonitorRequest monitorRequest : monitorRequests) {
//            Server server = monitorRequest.getServer();
//            List<Ports> ports = server.getPorts();
//            log.info("Server: {}, Ports: {}", server, ports);
//
//            checkAndSendIpNotification(server, monitorRequest.getUsers());
//            checkAndSendPortNotifications(server, monitorRequest.getUsers());
//        }
//
//        log.info("Scheduled task to monitor IP and Port completed.");
//    }
//
//    private void checkAndSendIpNotification(Server server, List<User> users) {
//        boolean isHostReachable = checkIpAddress(server);
//
//        // Update isActive field based on IP reachability
//        server.setIsActive(isHostReachable);
//        
//        if (!isHostReachable || prvState.getOrDefault(server, false)) {
//            log.warn("IP {} is {}reachable.", server.getHost(), isHostReachable ? "" : "not ");
//            processIpNotification(server, users, isHostReachable);
//        }
//
//        prvState.put(server, isHostReachable);
//    }
//
//    private void processIpNotification(Server server, List<User> users, boolean isHostReachable) {
//        if (!isHostReachable) {
//            log.warn("Sending email for unreachable IP: {}", server.getHost());
//            sendIpNotReachableEmails(users, server);
//        } else {
//            log.info("Sending email for reachable IP: {}", server.getHost());
//            sendIpReachableEmails(users, server);
//        }
//    }
//
//    private void checkAndSendPortNotifications(Server server, List<User> users) {
//        boolean arePortsReachable = checkPortsReachable(server);
//
//        updatePortsIsActive(server, arePortsReachable);
//        if (!arePortsReachable) {
//            log.warn("Ports on IP {} are not reachable.", server.getHost());
//            processPortNotifications(server, users, arePortsReachable);
//        }
//    }
//
//    private void updatePortsIsActive(Server server, boolean arePortsReachable) {
//        List<Ports> ports = server.getPorts();
//        for (Ports port : ports) {
//            port.setIsActive(arePortsReachable);
//        }
//    }
//    private void processPortNotifications(Server server, List<User> users, boolean arePortsReachable) {
//        if (!arePortsReachable) {
//            log.warn("Sending email for unreachable ports on IP: {}", server.getHost());
//            sendPortNotReachableEmails(users, server);
//        } else {
//            log.info("Sending email for reachable ports on IP: {}", server.getHost());
//            sendPortReachableEmails(users, server);	
//        }
//    }
//
//    private void sendIpNotReachableEmails(List<User> users, Server server) {
//        for (User user : users) {
//            if (!isEmailSentForUnreachableIp(user, server)) {
//                sendIpNotReachableEmail(user, server);
//            }
//        }
//    }
//
//    private void sendIpReachableEmails(List<User> users, Server server) {
//        for (User user : users) {
//            if (!isEmailSentForReachableIp(user, server)) {
//                sendIpReachableEmail(user, server);
//            }
//        }
//    }
//
//    private boolean checkPortsReachable(Server server) {
//        List<Ports> ports = server.getPorts();
//        for (Ports port : ports) {
//            if (!checkPortReachable(server, port)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void sendPortNotReachableEmails(List<User> users, Server server) {
//        for (User user : users) {
//            if (!isEmailSentForUnreachablePort(user, server)) {
//                sendPortNotReachableEmail(user, server);
//            }
//        }
//    }
//
//    private void sendPortReachableEmails(List<User> users, Server server) {
//        for (User user : users) {
//            if (!isEmailSentForReachablePort(user, server)) {
//                sendPortReachableEmail(user, server);
//            }
//        }
//    }
//
//    private void sendIpNotReachableEmail(User user, Server server) {
//        try {
//            String subject = "IP Not Reachable Alert";
//            String message = "The IP address " + server.getHost() + " is not reachable.";
//
//            boolean isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//            MonitorStatus monitorStatus = new MonitorStatus();
//            monitorStatus.setUser(user);
//            monitorStatus.setIsSent(isSent);
//            monitorStatus.setSentAt(new Date());
//            monitorStatus.setServer(server);
//
//            msService.addMonitorStatus(monitorStatus);
//
//            log.info("Email notification sent to {} successfully for unreachable IP.", user);
//        } catch (Exception ex) {
//            handleEmailSendingError(user, server, "unreachable IP", ex);
//        }
//    }
//
//    private void sendIpReachableEmail(User user, Server server) {
//        try {
//            String subject = "IP Reachable Alert";
//            String message = "The IP address " + server.getHost() + " is reachable again.";
//
//            boolean isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//            MonitorStatus monitorStatus = new MonitorStatus();
//            monitorStatus.setUser(user);
//            monitorStatus.setIsSent(isSent);
//            monitorStatus.setSentAt(new Date());
//            monitorStatus.setServer(server);
//
//            msService.addMonitorStatus(monitorStatus);
//
//            log.info("Email notification sent to {} successfully for reachable IP.", user);
//        } catch (Exception ex) {
//            handleEmailSendingError(user, server, "reachable IP", ex);
//        }
//    }
//
//    private boolean checkPortReachable(Server server, Ports port) {
//        try (Socket socket = new Socket(server.getHost(), port.getPorts())) {
//            log.info("Port {} is reachable on server {}", port.getPorts(), server.getHost());
//            return true;
//        } catch (ConnectException e) {
//            log.warn("Connection to port {} on server {} refused. The port might be closed or the service is not running.", port.getPorts(), server.getHost());
//            return false;
//        } catch (SocketTimeoutException e) {
//            log.warn("Connection to port {} on server {} timed out.", port.getPorts(), server.getHost());
//            return false;
//        } catch (UnknownHostException e) {
//            log.error("Unknown host: {}", server.getHost());
//            return false;
//        } catch (IOException e) {
//            log.warn("Port {} is not reachable on server {}: {}", port.getPorts(), server.getHost(), e.getMessage());
//            return false;
//        } catch (Exception e) {
//            log.error("Unexpected exception while checking port {} on server {}: {}", port.getPorts(), server.getHost(), e.getMessage());
//            return false;
//        }
//    }
//
//
//    private void sendPortNotReachableEmail(User user, Server server) {
//        try {
//            String subject = "Port Not Reachable Alert";
//            String message = "The ports on IP address " + server.getHost() + " are not reachable.";
//
//            boolean isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//            MonitorStatus monitorStatus = new MonitorStatus();
//            monitorStatus.setUser(user);
//            monitorStatus.setIsSent(isSent);
//            monitorStatus.setSentAt(new Date());
//            monitorStatus.setServer(server);
//
//            msService.addMonitorStatus(monitorStatus);
//
//            log.info("Email notification sent to {} successfully for unreachable ports.", user);
//        } catch (Exception ex) {
//            handleEmailSendingError(user, server, "unreachable ports", ex);
//        }
//    }
//
//    private void sendPortReachableEmail(User user, Server server) {
//        try {
//            String subject = "Port Reachable Alert";
//            String message = "The ports on IP address " + server.getHost() + " are reachable again.";
//
//            boolean isSent = emailService.sendEmail(user.getEMail(), subject, message);
//
//            MonitorStatus monitorStatus = new MonitorStatus();
//            monitorStatus.setUser(user);
//            monitorStatus.setIsSent(isSent);
//            monitorStatus.setSentAt(new Date());
//            monitorStatus.setServer(server);
//
//            msService.addMonitorStatus(monitorStatus);
//
//            log.info("Email notification sent to {} successfully for reachable ports.", user);
//        } catch (Exception ex) {
//            handleEmailSendingError(user, server, "reachable ports", ex);
//        }
//    }
//
//    private void handleEmailSendingError(User user, Server server, String type, Exception ex) {
//        log.error("Failed to send email notification to {} for {}: {}", user, type, ex.getMessage());
//
//        MonitorStatus monitorStatus = new MonitorStatus();
//        monitorStatus.setUser(user);
//        monitorStatus.setIsSent(false);
//        monitorStatus.setSentAt(new Date());
//        monitorStatus.setServer(server);
//
//        msService.addMonitorStatus(monitorStatus);
//    }
//
//    private boolean checkIpAddress(Server server) {
//        if (server != null && server.getHost() != null) {
//            return checkIpAddress(server.getHost());
//        }
//        return false;
//    }
//
//    private boolean checkIpAddress(String host) {
//        try {
//            boolean isReachable = InetAddress.getByName(host).isReachable(5000);
//            if (isReachable) {
//                log.info("IP {} is reachable.", host);
//            } else {
//                log.warn("IP {} is not reachable.", host);
//            }
//            return isReachable;
//        } catch (IOException e) {
//            log.error("Exception while checking IP address: {}", e.getMessage());
//            return false;
//        }
//    }
//
//
//    private boolean isEmailSentForUnreachableIp(User user, Server server) {
//        return isEmailSentForType(user, server, "unreachable IP");
//    }
//
//    private boolean isEmailSentForReachableIp(User user, Server server) {
//        return isEmailSentForType(user, server, "reachable IP");
//    }
//
//    private boolean isEmailSentForUnreachablePort(User user, Server server) {
//        return isEmailSentForType(user, server, "unreachable ports");
//    }
//
//    private boolean isEmailSentForReachablePort(User user, Server server) {
//        return isEmailSentForType(user, server, "reachable ports");
//    }
//
//    private boolean isEmailSentForType(User user, Server server, String type) {
//        List<MonitorStatus> monitorStatuses = msService.getMonitorStatusesByUser(user);
//
//        for (MonitorStatus monitorStatus : monitorStatuses) {
//            if (monitorStatus.getIsSent() && monitorStatus.getServer().equals(server)) {
//                log.info("Email already sent to {} for {}.", user, type);
//                return true;
//            }
//        }
//        return false;
//    }
//}
