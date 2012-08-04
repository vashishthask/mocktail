package org.mocktail;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class UserDetailServiceTest  extends AbstractDbUnitTest{

	private UserDetailService userDetailService;
	private EntityManagerFactory emf;

	@Before
	public void setup() throws Exception {
		emf = Persistence.createEntityManagerFactory("mocktail-orm");
		userDetailService = new UserDetailService();
		super.init();
	}

	@Test
	public void shouldSaveUserDetail() {
		userDetailService.saveUserDetail(new UserDetail("user"));
	}

	@Override
	protected Connection getSqlConnection() throws SQLException {
		Session session = (Session)emf.createEntityManager().getDelegate();
		Connection conn = session.connection();
		return conn;
	}

	@Override
	protected String getDataSetFile() {
		return "src/test/resources/org/mocktail/UserDetail-dataset.xml";
	}
}
