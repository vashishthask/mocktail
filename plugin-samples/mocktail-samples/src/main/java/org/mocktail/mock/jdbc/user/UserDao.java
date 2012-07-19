package org.mocktail.mock.jdbc.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mocktail.mock.jdbc.AbstractGenericJDBCDao;
import org.mocktail.mock.jdbc.JdbcExecutor;


public class UserDao extends AbstractGenericJDBCDao<UserDetail> {

	@Override
	protected String getLoadQuery(Long id) {
		
		return "select * from userdetail where id = "+id;
	}

	@Override
	protected UserDetail mapResultSetToEntity(ResultSet resultSet) {
		UserDetail userDetail = new UserDetail();
		try {
			resultSet.next();
			userDetail.setId(resultSet.getInt(1));
			userDetail.setAge(resultSet.getInt(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDetail;
	}

    @Override
    public void delete(UserDetail e) {
        
    }

    @Override
    public void save(UserDetail userDetail) {
        JdbcExecutor jdbcExecutor = new JdbcExecutor();
        Connection connection = jdbcExecutor.getConnection();
        String insertStatement = "insert into userdetail values(?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertStatement);
            statement.setInt(1, userDetail.getId());
            statement.setInt(2, userDetail.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
