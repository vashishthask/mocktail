package org.mocktail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class UserDetailService {

	private SessionFactory sessionFactory;

	public UserDetailService() {
		sessionFactory = new AnnotationConfiguration().configure(
				"SessionFactoryConfig.cfg.xml").buildSessionFactory();
	}
	
	public UserDetail getUserDetail() {
		Session session = sessionFactory.getCurrentSession();
		UserDetail userDetail = (UserDetail) session.get(UserDetail.class, 1L);
		return userDetail;
	}
}
