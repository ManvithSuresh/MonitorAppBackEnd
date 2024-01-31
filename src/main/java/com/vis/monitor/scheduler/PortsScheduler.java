package com.vis.monitor.scheduler;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vis.monitor.modal.GroupingPorts;
import com.vis.monitor.modal.ServerGroup;

@Component
public class PortsScheduler {

    private Queue<GroupingPorts> portsQueue = new LinkedList<>();
    private int maxSize = 5; 
    @Scheduled(fixedDelay = 1000)
    public void checkingPorts() {
        try {
            ServerGroup serverGroup = new ServerGroup();
            List<GroupingPorts> services = serverGroup.getServices();

            if (services != null) {
                for (GroupingPorts service : services) {
                    if (service != null) {
                        portsQueue.offer(service);

                        // Manage the size of the queue
                        while (portsQueue.size() > maxSize) {
                            portsQueue.poll(); // Remove the oldest element
                        }
                    }
                }

                // Check reachability of ports
                for (GroupingPorts currentPort : portsQueue) {
                    int port = currentPort.getPort();
                    boolean isReachable = isPortReachable("localhost", port, 1000); 
                    
                    System.out.println("Port: " + port + ", isReachable: " + isReachable);
                    // Do something with the information
                }
            } else {
                System.out.println("No services available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isPortReachable(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
