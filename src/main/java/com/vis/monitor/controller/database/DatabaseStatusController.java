package com.vis.monitor.controller.database;



import com.vis.monitor.db.modal.DatabaseStatus;
import com.vis.monitor.service.DatabaseStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DatabaseStatusController {

    private final DatabaseStatusService databaseStatusService;

    @Autowired
    public DatabaseStatusController(DatabaseStatusService databaseStatusService) {
        this.databaseStatusService = databaseStatusService;
    }

    @GetMapping("/{id}")
    public DatabaseStatus getDatabaseStatus(@PathVariable Long id) {
        return databaseStatusService.getDatabaseStatus(id);
    }

    @GetMapping("/getDatabaseStatuses")
    public List<DatabaseStatus> getAllDatabaseStatuses() {
        return databaseStatusService.getAllDatabaseStatuses();
    }

    @PostMapping("/add")
    public DatabaseStatus addDatabaseStatus(@RequestBody DatabaseStatus databaseStatus) {
        return databaseStatusService.addDatabaseStatus(databaseStatus);
    }

    @PutMapping("/update/{id}")
    public DatabaseStatus updateDatabaseStatus(@PathVariable Long id, @RequestBody DatabaseStatus databaseStatus) {
        return databaseStatusService.updateDatabaseStatus(id, databaseStatus);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDatabaseStatus(@PathVariable Long id) {
        databaseStatusService.deleteDatabaseStatus(id);
    }
}
