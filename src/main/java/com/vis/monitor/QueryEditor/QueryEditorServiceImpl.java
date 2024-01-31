package com.vis.monitor.QueryEditor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vis.monitor.db.modal.Database;
import com.vis.monitor.repository.DatabaseRepository;
import com.vis.monitor.service.DatabaseService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class QueryEditorServiceImpl implements QueryEditorService {

    @Autowired
    private QueryEditorRepository queryEditorRepository;
    @Autowired
    private DatabaseService databaseService;

    
    @Autowired 
    private DatabaseRepository databaseRepository;
    
	@Autowired
	private DatabaseRepository dbRepo;

	@Override
	public QueryEditor saveQueryEditor(QueryEditor queryEditor) {
		// TODO Auto-generated method stub
		return queryEditorRepository.save(queryEditor);
	}
	
	
	   
}