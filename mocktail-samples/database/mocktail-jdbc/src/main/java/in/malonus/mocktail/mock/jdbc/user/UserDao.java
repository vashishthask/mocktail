package in.malonus.mocktail.mock.jdbc.user;

import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hsqldb.jdbcDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import in.malonus.mocktail.mock.jdbc.GenericDao;

public class UserDao implements GenericDao<UserDetail> {

    private static final String GET_OBJECT_QUERY = "select id, age from userdetail where id = ?";
    private static final String GET_ALL_OBJECTS_QUERY = "select id, age from userdetail";
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDao.class);

    private JdbcTemplate jdbcTemplate;

    public UserDao() {
        Driver driver = new jdbcDriver();
        DataSource dataSource = new SimpleDriverDataSource(driver, "jdbc:hsqldb:mem:mypersistence;user=sa");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserDetail get(Long id) {
        UserDetailRowMapper rowMapper = new UserDetailRowMapper();
        return jdbcTemplate.queryForObject(GET_OBJECT_QUERY, new Object[] { id }, rowMapper);
    }

    public int save(UserDetail userDetail) {
        LOGGER.debug("Insert with user detail:" + userDetail);
        return jdbcTemplate.update("insert into userdetail values(?,?)",
                new Object[] { new Integer(userDetail.getId()), new Integer(userDetail.getAge()) });
    }

    public int delete(UserDetail userDetail) {
        return this.jdbcTemplate.update("delete from userdetail where id=?",
                new Object[] { new Integer(userDetail.getId()) });
    }

    public int update(UserDetail userDetail) {
        return jdbcTemplate.update("update userdetail set age=? where id=?",
                new Object[] { new Integer(userDetail.getAge()), new Integer(userDetail.getId()) });
    }

    public List<UserDetail> getAll() {
        UserDetailRowMapper rowMapper = new UserDetailRowMapper();
        return jdbcTemplate.query(GET_ALL_OBJECTS_QUERY, rowMapper);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private class UserDetailRowMapper implements RowMapper<UserDetail> {

        public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDetail userDetail = new UserDetail();
            userDetail.setId(rs.getInt(1));
            userDetail.setAge(rs.getInt(2));
            return userDetail;
        }

    }

    public List<UserDetail> find(String query) {
        return null;
    }
}
