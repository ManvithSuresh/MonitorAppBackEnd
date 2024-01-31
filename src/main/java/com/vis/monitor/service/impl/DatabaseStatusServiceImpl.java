package com.vis.monitor.service.impl;



import com.vis.monitor.db.modal.Database;
import com.vis.monitor.db.modal.DatabaseStatus;
import com.vis.monitor.modal.Server;
import com.vis.monitor.modal.ServerStatus;
import com.vis.monitor.repository.DatabaseStatusRepository;
import com.vis.monitor.service.DatabaseStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseStatusServiceImpl implements DatabaseStatusService {

    private final DatabaseStatusRepository databaseStatusRepository;

    @Autowired
    public DatabaseStatusServiceImpl(DatabaseStatusRepository databaseStatusRepository) {
        this.databaseStatusRepository = databaseStatusRepository;
    }

    @Override
    public DatabaseStatus getDatabaseStatus(Long id) {
        Optional<DatabaseStatus> optionalDatabaseStatus = databaseStatusRepository.findById(id);
        return optionalDatabaseStatus.orElse(null);
    }

    @Override
    public List<DatabaseStatus> getAllDatabaseStatuses() {
        return databaseStatusRepository.findAll();
    }

    @Override
    public DatabaseStatus addDatabaseStatus(DatabaseStatus databaseStatus) {
        return databaseStatusRepository.save(databaseStatus);
    }

    @Override
    public DatabaseStatus updateDatabaseStatus(Long id, DatabaseStatus databaseStatus) {
        if (databaseStatusRepository.existsById(id)) {
            databaseStatus.setId(id);
            return databaseStatusRepository.save(databaseStatus);
        }
        return null;
    }

    @Override
    public DatabaseStatus getDatabaseStatusByDatabase(Database database) {
    	Optional <DatabaseStatus>oDatabase=databaseStatusRepository.findByDatabaseAndUpAtNull(database);
    	return oDatabase.isPresent()? oDatabase.get():null;
    }
    
    @Override
    public DatabaseStatus getTopDatabaseStatusByDatabase(Database database) {
    	Optional<DatabaseStatus> oDatabase =databaseStatusRepository.findTopByDatabaseOrderByDownAtDesc(database);
		return oDatabase.isPresent() ? oDatabase.get() : null;
	}

    
    @Override
    public void deleteDatabaseStatus(Long id) {
        databaseStatusRepository.deleteById(id);
    }

	
			
		

	@Override
	public List<DatabaseStatus> getDatabaseStatusByDatabaseAndStartTimeAndEndTime(Database database, Date startDate,
			Date endDate) {
		List<DatabaseStatus > databaseStatuses = databaseStatusRepository.findByDatabaseAndUpAtBetweenOrderByDownAt(database, startDate, endDate);
		
		List<DatabaseStatus > databaseUpAtNullStatuses = databaseStatusRepository.findByDatabaseAndUpAtNullOrderByDownAt(database);
		
		databaseStatuses.addAll(databaseUpAtNullStatuses);
		
		return databaseStatuses; 
	
	}
	}

