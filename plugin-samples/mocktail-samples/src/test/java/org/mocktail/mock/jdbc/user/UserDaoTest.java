package org.mocktail.mock.jdbc.user;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mocktail.mock.jdbc.JdbcExecutor;

public class UserDaoTest {

	private JdbcExecutor jdbcExecutor;
	UserDao userDao;
	
	   
	   

	@Before
	public void setup() {
	    userDao = new UserDao();
		jdbcExecutor = new JdbcExecutor();
		jdbcExecutor.execute("insert into USERDETAIL values (1,10)");
	}
	
	@BeforeClass
	public static void cleanRecordings(){
	    JdbcExecutor jdbcExecutor = new JdbcExecutor();
	    jdbcExecutor.execute("CREATE TABLE USERDETAIL (id INTEGER,age INTEGER)");
	}
	
	@Test
	public void testGetUser() {
		
		//search with recording mode
        UserDetail userDetail = userDao.get(1L);
        assertNotNull(userDetail);
        assertThat(10, is(userDetail.getAge()));
        
        
        //update userdetail record with new value
        jdbcExecutor.execute("update userdetail set age=12 where id=1");
        
        //search again
        UserDetail recordedUserDetail = userDao.get(1L);
        assertEquals(10, recordedUserDetail.getAge());
	}
	
	@Test
    public void testInsertUser() {
	    int count = getNumRows();
	    
	    assertEquals(1, count);
	    UserDetail userDetail = new UserDetail();
	    userDetail.setAge(20);
	    userDetail.setId(2);

        //insert the row in recording mode
	    userDao.save(userDetail);
	    count = getNumRows();
	    
	    assertEquals(2, count);
	    
	    userDetail.setAge(20);
        userDetail.setId(3);
        userDao.save(userDetail);
        
        count = getNumRows();
        
        assertEquals(2, count);
    }

    private int getNumRows() {
        ResultSet resultSet = jdbcExecutor.executeQueryResultSet("select count(*) from userdetail");
	    int count = 0;
	    try {
            while (resultSet.next()){
                count = resultSet.getInt(1);
                System.out.println("The id is:"+count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
	
	@After
	public void tearDown(){
	    jdbcExecutor.execute("delete from USERDETAIL");
	}
}
