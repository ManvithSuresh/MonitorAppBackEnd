//package com.vis.monitor.modal;
//
//
//
//import com.vis.monitor.modal.Ports;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class PortsController {
//
//    @Autowired
//    private PortsService portsService;
//
//    @PostMapping("/add-ports")
//    public ResponseEntity<Ports> createPort(@RequestBody Ports port) {
//        Ports savedPort = portsService.savePort(port);
//        return ResponseEntity.ok(savedPort);
//    }
//
//    @GetMapping("/get-ports-by/{id}")
//    public ResponseEntity<Ports> getPort(@PathVariable Long id) {
//        Ports port = portsService.getPortById(id);
//        if (port != null) {
//            return ResponseEntity.ok(port);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/get-all-ports")
//    public ResponseEntity<List<Ports>> getAllPorts() {
//        List<Ports> portsList = portsService.getAllPorts();
//        return ResponseEntity.ok(portsList);
//    }
//
//    @DeleteMapping("/delete-ports-by/{id}")
//    public ResponseEntity<Void> deletePort(@PathVariable Long id) {
//        portsService.deletePort(id);
//        return ResponseEntity.noContent().build();
//    }
//}
