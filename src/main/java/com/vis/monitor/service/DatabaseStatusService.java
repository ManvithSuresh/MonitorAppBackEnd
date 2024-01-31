package com.vis.monitor.service;



import com.vis.monitor.db.modal.Database;
import com.vis.monitor.db.modal.DatabaseStatus;

import java.util.Date;
import java.util.List;

public interface DatabaseStatusService {
    DatabaseStatus getDatabaseStatus(Long id);
    List<DatabaseStatus> getAllDatabaseStatuses();
    DatabaseStatus addDatabaseStatus(DatabaseStatus databaseStatus);
    DatabaseStatus updateDatabaseStatus(Long id, DatabaseStatus databaseStatus);
    void deleteDatabaseStatus(Long id);
	DatabaseStatus getTopDatabaseStatusByDatabase(Database database);
	List<DatabaseStatus> getDatabaseStatusByDatabaseAndStartTimeAndEndTime(Database database, Date startDate,
			Date endDate);
	DatabaseStatus getDatabaseStatusByDatabase(Database database);
}
