package org.mocktail.mock.jdbc.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mocktail.mock.jdbc.AbstractGenericJDBCDao;

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
			userDetail.setId(resultSet.getLong(1));
			userDetail.setAge(resultSet.getInt(2));
		} catch (SQLException e) {
			System.out.println("Got sql error while reading result set");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDetail;
	}

}
