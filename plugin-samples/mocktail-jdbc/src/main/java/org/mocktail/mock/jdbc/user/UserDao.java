package org.mocktail.mock.jdbc.user;

import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hsqldb.jdbcDriver;
import org.mocktail.mock.jdbc.GenericJDBCDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


public class UserDao implements GenericJDBCDao<UserDetail> {
    
    private static final String GET_OBJECT_QUERY = "select id, age from userdetail where id = ?";
    
    private JdbcTemplate jdbcTemplate;
    
    public UserDao(){
        Driver driver = new jdbcDriver();
        DataSource dataSource = new SimpleDriverDataSource(driver, "jdbc:hsqldb:mem:mypersistence;user=sa");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public UserDetail get(Long id) {
       return jdbcTemplate.queryForObject(GET_OBJECT_QUERY, new Object[] {id}, new RowMapper<UserDetail>(){

        public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDetail userDetail = new UserDetail();
            userDetail.setId(rs.getInt(1));
            userDetail.setAge(rs.getInt(2));
            return userDetail;
        }
           
       });
    }
    
    public int save(UserDetail userDetail) {
        return jdbcTemplate.update("insert into userdetail values(?,?)", new Object[]{new Integer(userDetail.getId()), new Integer(userDetail.getAge())});
    }


    public int delete(UserDetail userDetail) {
        return this.jdbcTemplate.update("delete from userdetail where id=?", new Object []{new Integer(userDetail.getId())});
    }

    public int update(UserDetail userDetail) {
        return jdbcTemplate.update("update userdetail set age=? where id=?", new Object[]{new Integer(userDetail.getAge()), new Integer(userDetail.getId())});
    }

    public List<UserDetail> find(String query) {
        return null;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;   
    }
}
