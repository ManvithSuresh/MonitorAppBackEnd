//package com.vis.monitor.scheduler;
//
//import java.io.IOException;
//import java.net.ConnectException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.vis.monitor.modal.GroupingPorts;
//import com.vis.monitor.modal.MonitorRequest;
//import com.vis.monitor.modal.MonitorStatus;
//import com.vis.monitor.modal.Server;
//import com.vis.monitor.modal.ServerGroup;
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
//public class DemoScheduler {
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
//    @Scheduled(fixedDelay = 1000)
//    public void checkIpPortsAndSendEmails() {
//        log.info("Scheduled task to monitor IP and Port started.");
//
//        List<MonitorRequest> monitorRequests = mrService.getMonitorRequests();
//        log.info("Total requests: {}", monitorRequests.size());
//
//        if (!monitorRequests.isEmpty()) {
//            for (MonitorRequest monitorRequest : monitorRequests) {
//                Server server = monitorRequest.getServer();
//              
//                // Check and send IP notifications
//                checkAndSendIpNotification(server, monitorRequest.getUsers());
//
//            }
//        } else {
//            log.warn("No MonitorRequests available.");
//        }
//    }
//
//    private void checkAndSendIpNotification(Server server, List<User> users) {
//        boolean isHostReachable = checkIpAddress(server);
//
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
//
//
//
//
//
//  
//
//    private void checkPortReachable(Server server, ServerGroup serverGroup, GroupingPorts groupingPorts, List<User> users) {
//        List<Integer> ports = groupingPorts.getPorts();
//
//        for (Integer port : ports) {
//            try (Socket socket = new Socket()) {
//                socket.connect(new InetSocketAddress(server.getHost(), port), 5000);
//                log.info("Port {} is reachable on server {} in server group {}", port, server.getHost(), serverGroup.getGroupName());
//            } catch (ConnectException e) {
//                log.warn("Connection to port {} on server {} in server group {} refused. The port might be closed or the service is not running.", port, server.getHost(), serverGroup.getGroupName());
//                processPortNotification(server, users, serverGroup, false, port);
//            } catch (SocketTimeoutException e) {
//                log.warn("Connection to port {} on server {} in server group {} timed out.", port, server.getHost(), serverGroup.getGroupName());
//                processPortNotification(server, users, serverGroup, false, port);
//            } catch (UnknownHostException e) {
//                log.error("Unknown host: {}", server.getHost());
//                processPortNotification(server, users, serverGroup, false, port);
//            } catch (IOException e) {
//                log.warn("Port {} is not reachable on server {} in server group {}: {}", port, server.getHost(), serverGroup.getGroupName(), e.getMessage());
//                processPortNotification(server, users, serverGroup, false, port);
//            } catch (Exception e) {
//                log.error("Unexpected exception while checking port {} on server {} in server group {}: {}", port, server.getHost(), serverGroup.getGroupName(), e.getMessage());
//                processPortNotification(server, users, serverGroup, false, port);
//            }
//        }
//    }
//
//    private void processPortNotification(Server server, List<User> users, ServerGroup serverGroup, boolean isPortReachable, int port) {
//        if (!isPortReachable) {
//            log.warn("Sending email for unreachable port {} on server {} in server group {}.", port, server.getHost(), serverGroup.getGroupName());
//            sendPortNotReachableEmails(users, server, port);
//        } else {
//            log.info("Port {} is reachable again on server {} in server group {}.", port, server.getHost(), serverGroup.getGroupName());
//            sendPortReachableEmails(users, server, port);
//        }
//    }
//
//    private void sendPortNotReachableEmails(List<User> users, Server server, int port) {
//        for (User user : users) {
//            if (!isEmailSentForUnreachablePort(user, server, port)) {
//                sendPortNotReachableEmail(user, server, port);
//            }
//        }
//    }
//
//    private void sendPortReachableEmails(List<User> users, Server server, int port) {
//        for (User user : users) {
//            if (!isEmailSentForReachablePort(user, server, port)) {
//                sendPortReachableEmail(user, server, port);
//            }
//        }
//    }
//
//    private void sendPortNotReachableEmail(User user, Server server, int port) {
//        try {
//            String subject = "Port Not Reachable Alert";
//            String message = "The port " + port + " on server " + server.getHost() + " is not reachable.";
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
//            log.info("Email notification sent to {} successfully for unreachable port.", user);
//        } catch (Exception ex) {
//            handleEmailSendingError(user, server, "unreachable port", ex);
//        }
//    }
//
//    private void sendPortReachableEmail(User user, Server server, int port) {
//        try {
//            String subject = "Port Reachable Alert";
//            String message = "The port " + port + " on server " + server.getHost() + " is reachable again.";
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
//            log.info("Email notification sent to {} successfully for reachable port.", user);
//        } catch (Exception ex) {
//            handleEmailSendingError(user, server, "reachable port", ex);
//        }
//    }
//
//    private boolean isEmailSentForUnreachablePort(User user, Server server, int port) {
//        return isEmailSentForType(user, server, "unreachable port", port);
//    }
//
//    private boolean isEmailSentForReachablePort(User user, Server server, int port) {
//        return isEmailSentForType(user, server, "reachable port", port);
//    }
//
//    private boolean isEmailSentForType(User user, Server server, String type, int port) {
//        List<MonitorStatus> monitorStatuses = msService.getMonitorStatusesByUser(user);
//
//        for (MonitorStatus monitorStatus : monitorStatuses) {
//            if (monitorStatus.getIsSent() && monitorStatus.getServer().equals(server)) {
//                log.info("Email already sent to {} for {} on port {}.", user, type, port);
//                return true;
//            }
//        }
//        return false;
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
//    private boolean isEmailSentForUnreachableIp(User user, Server server) {
//        return isEmailSentForType(user, server, "unreachable IP");
//    }
//
//    private boolean isEmailSentForReachableIp(User user, Server server) {
//        return isEmailSentForType(user, server, "reachable IP");
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
