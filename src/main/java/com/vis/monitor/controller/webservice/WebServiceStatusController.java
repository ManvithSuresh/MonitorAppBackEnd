package com.vis.monitor.controller.webservice;



import com.vis.monitor.ws.modal.WebServiceStatus;
import com.vis.monitor.service.WebServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/webservicestatus")
public class WebServiceStatusController {

    private final WebServiceStatusService webServiceStatusService;

    @Autowired
    public WebServiceStatusController(WebServiceStatusService webServiceStatusService) {
        this.webServiceStatusService = webServiceStatusService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebServiceStatus> getWebServiceStatus(@PathVariable Long id) {
        WebServiceStatus webServiceStatus = webServiceStatusService.getWebServiceStatus(id);
        if (webServiceStatus != null) {
            return ResponseEntity.ok(webServiceStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<WebServiceStatus>> getAllWebServiceStatuses() {
        List<WebServiceStatus> webServiceStatuses = webServiceStatusService.getAllWebServiceStatuses();
        return ResponseEntity.ok(webServiceStatuses);
    }

    @PostMapping("/add")
    public ResponseEntity<WebServiceStatus> addWebServiceStatus(@RequestBody WebServiceStatus webServiceStatus) {
        WebServiceStatus addedStatus = webServiceStatusService.addWebServiceStatus(webServiceStatus);
        return ResponseEntity.ok(addedStatus);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WebServiceStatus> updateWebServiceStatus(@PathVariable Long id, @RequestBody WebServiceStatus webServiceStatus) {
        WebServiceStatus updatedStatus = webServiceStatusService.updateWebServiceStatus(id, webServiceStatus);
        if (updatedStatus != null) {
            return ResponseEntity.ok(updatedStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWebServiceStatus(@PathVariable Long id) {
        webServiceStatusService.deleteWebServiceStatus(id);
        return ResponseEntity.noContent().build();
    }
}
