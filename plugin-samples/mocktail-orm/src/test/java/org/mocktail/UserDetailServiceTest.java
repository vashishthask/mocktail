package org.mocktail;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

	@Test
	public void shouldGetUserDetail() throws Exception {
		UserDetail userDetail = userDetailService.getUserDetail(1L);
		
		assertNotNull(userDetail);
		assertSame(1L, userDetail.getId());
		assertEquals("Got " + userDetail.getName(), "user1", userDetail.getName());
	}
	
	@Test
	public void shouldGetAllUsers() throws Exception {
		List<UserDetail> users = userDetailService.getAllUsers();
		
		assertNotNull(users);
		assertEquals(3, users.size());
		assertEquals("user1", users.get(0).getName());
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
