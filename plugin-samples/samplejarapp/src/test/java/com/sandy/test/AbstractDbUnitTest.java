package com.sandy.test;

import java.io.File;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

public abstract class AbstractDbUnitTest {

	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void init() throws Exception {
		System.out.println("Cleaning & inserting data");
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}

	@After
	public void after() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
	}

	private IDataSet getDataSet() throws Exception {
		File file = new File(getDataSetFile());
		return new FlatXmlDataSet(file);
	}

	private IDatabaseConnection getConnection() {
		Connection con = DataSourceUtils.getConnection(dataSource);
		IDatabaseConnection dbUnitCon = new DatabaseConnection(con);
		return dbUnitCon;
	}
	
	protected abstract String getDataSetFile();
}
