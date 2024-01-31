//package com.vis.monitor.controller;
//
//
//
//import java.util.List;
//
//import com.vis.monitor.db.modal.Database;
//import com.vis.monitor.service.DatabaseService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/databases")
//public class DatabaseController {
//
//    private final DatabaseService databaseService;
//
//    @Autowired
//    public DatabaseController(DatabaseService databaseService) {
//        this.databaseService = databaseService;
//    }
//
//    @PostMapping("/addDatabase")
//    public Database addDatabase(@RequestBody Database database) {
//        return databaseService.addDatabase(database);
//    }
//
//    @PutMapping("/updateDatabase")
//    public Database updateDatabase(@RequestBody Database database) {
//        return databaseService.updateDatabase(database);
//    }
//
//    @GetMapping("/getDatabases")
//    public List<Database> getDatabases() {
//        return databaseService.getDatabase();
//    }
//
//    @GetMapping("/getDatabases/{id}")
//    public Database getDatabase(@PathVariable Long id) {
//        return databaseService.getDatabase(id);
//    }
//
//    @DeleteMapping("/deleteDatabases/{id}")
//    public Database deleteDatabase(@PathVariable Long id) {
//        return databaseService.deleteDatabase(id);
//    }
//}

package com.vis.monitor.controller.database;

import com.vis.monitor.QueryEditor.DynamicDataSource;
import com.vis.monitor.database.fetchers.MSSQLTableFetcher;
import com.vis.monitor.database.fetchers.MySQLTableFetcher;
import com.vis.monitor.db.modal.Database;
import com.vis.monitor.db.modal.DatabaseHourlyStatus;
import com.vis.monitor.service.DatabaseService;
import com.vis.monitor.service.DatabaseStatusService;

import lombok.extern.log4j.Log4j2;

import com.vis.monitor.db.modal.DatabaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

@RestController
@RequestMapping("/api")
@Log4j2
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private DatabaseStatusService dbStatusService;

//    @PostMapping("/add-database")
//    public ResponseEntity<Database> addDatabase(@RequestBody Database database) {
//        Database addedDatabase = databaseService.addDatabase(database);
//        return ResponseEntity.ok(addedDatabase);
//    }

    @PostMapping("/add-database")
    public ResponseEntity<Database> addDatabase(@RequestBody Database database) {
        // Save the database details
        Database addedDatabase = databaseService.addDatabase(database);

        // Fetch and set table names based on the database type
        List<String> tableNames;
        if ("mysql".equalsIgnoreCase(database.getType())) {
            tableNames = MySQLTableFetcher.fetchTableNames(database);
            log.info("the table names are : " +tableNames);
            
        } else if ("mssql".equalsIgnoreCase(database.getType())) {
            tableNames = MSSQLTableFetcher.fetchTableNames(database);
        } else {
            // Handle other database types
            throw new UnsupportedOperationException("Database type not supported: " + database.getType());
        }

        // Update the database with table/collection names
        addedDatabase.setTables(String.join(",", tableNames));
        addedDatabase = databaseService.updateDatabase(addedDatabase);

        return ResponseEntity.ok(addedDatabase);
    }

    @PutMapping("/update-database")
    public ResponseEntity<Database> updateDatabase(@RequestBody Database database) {
        Database updatedDatabase = databaseService.updateDatabase(database);
        return ResponseEntity.ok(updatedDatabase);
    }

    @GetMapping("/get-databases")
    public ResponseEntity<List<Database>> getDatabases() {
        List<Database> databases = databaseService.getDatabase();
        return ResponseEntity.ok(databases);
    }

    @GetMapping("/get-database/{id}")
    public ResponseEntity<Database> getDatabaseById(@PathVariable Long id) {
        Database database = databaseService.getDatabase(id);
        if (database != null) {
            return ResponseEntity.ok(database);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-database/{id}")
    public ResponseEntity<Database> deleteDatabase(@PathVariable Long id) {
        Database deletedDatabase = databaseService.deleteDatabase(id);
        return ResponseEntity.ok(deletedDatabase);
    }

    @GetMapping("/get-database-status/{id}")
    public ResponseEntity<DatabaseStatus> getDatabaseStatus(@PathVariable Long id) {
        Database database = databaseService.getDatabase(id);
        if (database != null) {
            DatabaseStatus dbStatus = dbStatusService.getTopDatabaseStatusByDatabase(database);

            if (dbStatus != null) {
                return ResponseEntity.ok(dbStatus);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    

    @GetMapping("/get-database-hourly-status/{id}")
    public ResponseEntity<List<DatabaseHourlyStatus>> getDatabaseHourlyStatusByDatabaseId(@PathVariable Long id) {
        List<DatabaseHourlyStatus> hourlyStatus = new ArrayList<DatabaseHourlyStatus>();

        Map<String, DatabaseHourlyStatus> hmHourStatus = new LinkedHashMap<String, DatabaseHourlyStatus>();
        
        Database database = databaseService.getDatabase(id);

        if (database != null) {

            LocalDateTime now = LocalDateTime.now();

            String strDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

            String strEndTime = strDate + ":59:59";

            Date endDate = new Date();

            try {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            now = now.minusDays(1);

            strDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

            String strStartTime = strDate + ":00:00";

            Date startDate = new Date();

            try {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            List<DatabaseStatus> databaseStatuses = dbStatusService.getDatabaseStatusByDatabaseAndStartTimeAndEndTime(database,
                    startDate, endDate);

            Date stDate = startDate;
            Date stDateBackup = startDate;
            
            String nowDateHour = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
            String sDateHour = new SimpleDateFormat("yyyy-MM-dd HH").format(stDate);
            
            while (!nowDateHour.equals(sDateHour)) {
    
                Integer hour = null;
                Long duration = null;                
                
                for (DatabaseStatus databaseStatus : databaseStatuses) {

                    Date downAt = databaseStatus.getDownAt();

                    Date upAt = databaseStatus.getUpAt() != null ? databaseStatus.getUpAt() : new Date();

                    boolean isDownTimeFound = false;
                    
                    DatabaseHourlyStatus hourStatus = null;
                    
                    while (!nowDateHour.equals(sDateHour)) {
                    
                        hourStatus = null;
                        
                        Instant iNextHour = stDate.toInstant().plusSeconds(((59 * 60) + 59));

                        Date nextHour = Date.from(iNextHour);

                        boolean das = downAt.after(stDate);
                        boolean dbs = downAt.before(stDate);

                        boolean dbn = downAt.before(nextHour);

                        boolean des = downAt.equals(stDate);
                        boolean den = downAt.equals(nextHour);
                        
                        boolean uas = upAt.after(stDate);

                        boolean ubn = upAt.before(nextHour);
                        
                        boolean uen = upAt.equals(nextHour);

                        sDateHour = new SimpleDateFormat("yyyy-MM-dd HH").format(stDate);
                        
                        String sHour = new SimpleDateFormat("HH").format(stDate);
                        
                        hour = Integer.parseInt(sHour);
                        
                        duration = isDownTimeFound ? (nextHour.getTime() - stDate.getTime()) : 0L;
                        
                        if ((dbs || des) && (dbn || den)) {
                            // found start of down time and end of up time
                            duration = nextHour.getTime() - stDate.getTime(); 
                            isDownTimeFound = true;
                        }
                        
                        if ((das || des) && dbn) {
                            // found start of down time and end of up time
                            duration = nextHour.getTime() - downAt.getTime(); 
                            isDownTimeFound = true;
                        }

                        if (uas && ( ubn || uen)) {
                            // found end of down time and start of up time                            
                            if ((das || des) && dbn) {
                                // found start of down time and end of up time
                                duration = upAt.getTime() - downAt.getTime();
                                isDownTimeFound = true;
                            }else {
                                duration = upAt.getTime() - stDate.getTime();
                            }
                            stDateBackup = stDate;
                            break;
                        }
                        
                        stDateBackup = stDate;
                        
                        Instant iStartDate = nextHour.toInstant().plusSeconds(1);

                        stDate = Date.from(iStartDate);

                        iNextHour = stDate.toInstant().plusSeconds(((59 * 60) + 59));

                        nextHour = Date.from(iNextHour);

                        long diffInMillies = Math.abs(duration);
                        long seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        seconds = seconds == 0 ? 0 : seconds + 1;
                        
                        
                        if(hmHourStatus.containsKey(sDateHour)) {
                            hourStatus = hmHourStatus.get(sDateHour); 
                            seconds = hourStatus.getDuration() + seconds;
                        }
                        
                        if(hourStatus == null) {
                            
                            hourStatus = new DatabaseHourlyStatus(stDateBackup, stDateBackup, hour, seconds);
                        }
                        
                        hmHourStatus.put(sDateHour, hourStatus);
                    }
                    
                    long diffInMillies = Math.abs(duration);
                    long seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    seconds = seconds == 0 ? 0 : seconds + 1;
                    
                    if(hmHourStatus.containsKey(sDateHour)) {
                        hourStatus = hmHourStatus.get(sDateHour); 
                        seconds = hourStatus.getDuration() + seconds;
                    }
                    
                    if(hourStatus == null) {
                        hourStatus = new DatabaseHourlyStatus(stDateBackup, stDateBackup, hour, seconds);
                    }
                    
                    hmHourStatus.put(sDateHour, hourStatus);
                    
                }

            }
            
            hourlyStatus.addAll(hmHourStatus.values());
            
            return ResponseEntity.ok(hourlyStatus);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

        
    
}

