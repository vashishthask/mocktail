package org.mocktail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class UserDetailService {

	private EntityManagerFactory emf;

	public UserDetailService() {
		emf = Persistence.createEntityManagerFactory("mocktail-orm");
	}
	
	public UserDetail getUserDetail() {
		EntityManager newEm = emf.createEntityManager();
		EntityTransaction newTx = newEm.getTransaction();
		newTx.begin();

		UserDetail userDetail = newEm.find(UserDetail.class, 1L);
		newTx.commit();
		return userDetail;
	}

	public void saveUserDetail(UserDetail userDetail) {
		EntityManager newEm = emf.createEntityManager();
		EntityTransaction newTx = newEm.getTransaction();
		newTx.begin();
		
		newEm.persist(userDetail);
		newTx.commit();
		
	}
}
