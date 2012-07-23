package org.mocktail

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.mocktail.mock.jdbc.JdbcExecutor;
import org.mocktail.mock.jdbc.user.UserDao;
import org.mocktail.mock.jdbc.user.UserDetail;

JdbcExecutor jdbcExecutor;
UserDao userDao;
UserDetail recordedUserDetail;

before "create schema and remove recordings", {
    given "selenium is up and running", {

    }
}

scenario "Verify recording for search query",{
    
    
	given "data is already available in the db",{
        
	}
	
	when "search with same parameters again", {
        //search with recording mode
        
        //update userdetail record with new value
        
        //search again
        
	}
	
	then "data should be retrieved from recording", {
	}
}

scenario "Insert the record in database",{
    when "data is inserted in the database", {
    }
    
    then "nothing happens in the database", {
    }
}

scenario "Update the record in database",{
    when "data is updated in the database", {
    }
    
    then "nothing happens in the database", {
    }
}

scenario "delete the record in database",{
    when "record is deleted in the database", {
    }
    
    then "nothing happens in the database", {
    }
}

scenario "database schema changes which should break the tests",{
    given "record a test case",{
    }
    
    when "change the schema", {
    }
    
    then "test should break", {
    }
}

after "drop schema and remove recording" , {
    then "drop schema and remove recording", {
        jdbcExecutor.execute("delete from userdetail")
    }
}