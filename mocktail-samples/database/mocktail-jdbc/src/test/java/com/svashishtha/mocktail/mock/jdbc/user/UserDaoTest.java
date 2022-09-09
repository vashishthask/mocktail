package com.svashishtha.mocktail.mock.jdbc.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Driver;
import java.util.List;

import javax.sql.DataSource;

import org.hsqldb.jdbcDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.svashishtha.mocktail.metadata.MethodMocktail;

//FIXME test is not repeatable while running with "mvn install" after one run using "mvn clean install"
public class UserDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

    private JdbcTemplate jdbcTemplate;
    UserDao userDao;

    @Before
    public void setup() {
        userDao = new UserDao();
        jdbcTemplate = userDao.getJdbcTemplate();
        jdbcTemplate.update("insert into userdetail  values(1,10)");

    }

    @BeforeClass
    public static void cleanRecordings() {
        Driver driver = new jdbcDriver();
        DataSource dataSource = new SimpleDriverDataSource(driver,
                "jdbc:hsqldb:mem:mypersistence;user=sa");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute("CREATE TABLE USERDETAIL (id INTEGER,age INTEGER)");

    }

    @Test
    public void testGetUser() {
    	MethodMocktail methodMocktail = new MethodMocktail();
        methodMocktail.setUp(this);

        // search with recording mode
        UserDetail userDetail = userDao.get(1L);
        assertNotNull(userDetail);
        assertThat(10, is(userDetail.getAge()));

        // update userdetail record with new value
        jdbcTemplate.update("update userdetail set age=12 where id=1");

        // search again
        UserDetail recordedUserDetail = userDao.get(1L);
        assertEquals(10, recordedUserDetail.getAge());
    }

    @Test
    // FIXME testcase should be repeatable, i.e. should work without clean
    public void testInsertUser() {
    	MethodMocktail methodMocktail = new MethodMocktail();
        methodMocktail.setUp(this);
        boolean recordingAvailable = methodMocktail.areRecordingsAvailable();
        LOGGER.debug("isRecordingAvailable():"+recordingAvailable + " object details:"+methodMocktail);

        insertAnotherRow();// with 2, 20 cached
		int expectedRows = recordingAvailable? 1 : 2;
        //int expectedRows = 2;

        assertEquals(expectedRows, getNumRows());
        
        insertAnotherRowWithSameParamsAgain();// with 2, 20 shouldn't insert again
        expectedRows = recordingAvailable? 1 : 2;
        //expectedRows = 2;
        assertEquals(expectedRows, getNumRows());
    }

    private void insertAnotherRowWithSameParamsAgain() {
        insertAnotherRow();
    }

    private void insertAnotherRow() {
        UserDetail userDetail = new UserDetail();
        userDetail.setAge(20);
        userDetail.setId(3);
        int numAffected = userDao.save(userDetail);
        LOGGER.debug("numAffected:"+numAffected);
    }

    @Test
    // FIXME
    public void testUpdateUser() {
    	MethodMocktail methodMocktail = new MethodMocktail();
        methodMocktail.setUp(this);
        int count = getNumRows();

        assertEquals(1, count);
        UserDetail userDetail = new UserDetail();
        userDetail.setAge(30);
        userDetail.setId(1);

        // update the row in recording mode
        int affectedRows = userDao.update(userDetail);//update 1 with (1,30) - cached

        assertEquals(1, affectedRows);

        updateRecordToOriginalValue(1, 10);//externally updated
        createAnotherRecordExternally(1, 10);//another one created 1, 10 - total 2 records with key 1
        assertEquals(2, getNumRows());

        userDetail.setAge(30);
        userDetail.setId(1);
        int numRowsAffected = userDao.update(userDetail); //update again but this time as update query is cached it will not affect the db

        assertEquals(1, numRowsAffected); //numRowAffected comes from cached response and hence should be 1 instead of 2


    }

    private void updateRecordToOriginalValue(int id, int age) {
        jdbcTemplate.update("update userdetail set age=? where id=?",
                new Object[] { new Integer(age), new Integer(id) });
    }

    @Test
    public void testDeleteUser() {
    	MethodMocktail methodMocktail = new MethodMocktail();
        methodMocktail.setUp(this);
        boolean recordingAvailable = methodMocktail.areRecordingsAvailable();

        assertEquals(1, getNumRows());
        createAnotherRecordExternally(2, 20); //no recording 
        assertEquals(2, getNumRows()); //same response both in recording and playback

        deleteRecordWithUserDao(2);//response cached 
        int expectedRows = recordingAvailable ? 2 : 1;
        assertEquals(expectedRows, getNumRows()); //this should be 2 in playback, 1 in recording

        createAnotherRecordExternally(2, 20); //now the rows are 3 in playback, 2 in recording
        deleteRecordWithUserDao(2); // should not delete this time as response
                                    // of delete is cached in previous call

        expectedRows = recordingAvailable ? 3 : 2;
        assertEquals(expectedRows, getNumRows());
    }

    private UserDetail deleteRecordWithUserDao(int id) {
        UserDetail userDetail = new UserDetail();
        userDetail.setId(id);
        // insert the row in recording mode
        userDao.delete(userDetail);
        return userDetail;
    }

    private void createAnotherRecordExternally(int id, int age) {
        jdbcTemplate.update("insert into USERDETAIL values (" + id + "," + age
                + ")");
    }

    @Test
    public void testMethodBasedRecording() {
    	MethodMocktail methodMocktail = new MethodMocktail();
        methodMocktail.setUp(this);
        boolean recordingAvailable = methodMocktail.areRecordingsAvailable();

        // get all records, insert another one, get all records again. should be
        // n+1

        List<UserDetail> userDetails = userDao.getAll();
        assertThat(1, is(userDetails.size()));

        insertAnotherRow();

        userDetails = userDao.getAll();
        int expectedRows = recordingAvailable ? 1 : 2;
        assertThat(expectedRows, is(userDetails.size()));
    }
    

    private int getNumRows() {
        return jdbcTemplate.queryForInt("select count(*) from userdetail");
    }

    @After
    public void tearDown() {
        jdbcTemplate.update("delete from USERDETAIL");
    }
}
