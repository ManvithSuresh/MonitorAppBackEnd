package com.vis.monitor.database.status;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.modal.MonitorStatus;
import com.vis.monitor.modal.User;
import com.vis.monitor.repository.DatabaseStatusRepository;


@Service
public class MonitorDatabaseStatusServiceImpl implements MonitorDatabaseStatusService {

   @Autowired
   private MonitorDatabaseStatusRepository dbsRepo;
   
    @Override
    public List<MonitorDatabaseStatus> getMonitorDatabaseStatusesByUser(User user) {
       return dbsRepo.findByUser(user);
    }
    
    @Override
    public MonitorDatabaseStatus addMonitorDatabaseStatus(MonitorDatabaseStatus monitorDatabaseStatus) {
     return dbsRepo.save(monitorDatabaseStatus)   ;
    }

    @Override
    public MonitorDatabaseStatus updateMonitorDatabaseStatus(MonitorDatabaseStatus monitorDatabaseStatus) {
      return dbsRepo.save(monitorDatabaseStatus);
    }
    
    
    @Override
    public List<MonitorDatabaseStatus> getMonitorDatabaseStatuses() {
        return dbsRepo.findAll();
    }

    @Override
    public MonitorDatabaseStatus getMonitorDatabaseStatus(Long id) {
    Optional<MonitorDatabaseStatus>monitorDatabaseStatus=dbsRepo.findById(id);
    return monitorDatabaseStatus.isPresent()?monitorDatabaseStatus.get():null;
    }

    @Override
    public MonitorDatabaseStatus deleteMonitorDatabaseStatus(Long id) {
    Optional<MonitorDatabaseStatus> monitorDatabaseStatus=dbsRepo.findById(id);
     if(monitorDatabaseStatus.isPresent()) {
    	 dbsRepo.deleteById(id);	 
     }
    return monitorDatabaseStatus.isPresent()? monitorDatabaseStatus.get():null;
    }

    }
