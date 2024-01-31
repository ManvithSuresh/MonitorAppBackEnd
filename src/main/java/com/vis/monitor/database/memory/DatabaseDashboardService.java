package com.vis.monitor.database.memory;

import java.util.List;

public interface DatabaseDashboardService {
    List<DatabaseDashboardStatus> getServerStatus();
    
}
