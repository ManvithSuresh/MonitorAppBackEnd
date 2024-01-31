package com.vis.monitor.database.memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("system/api")
public class DatabaseDashboardController {

    @Autowired
    private DatabaseDashboardService databaseDashboardService;

    @GetMapping("/database")
    public List<DatabaseDashboardStatus> getDatabaseStatus() {
        return databaseDashboardService.getServerStatus();
    }
}
