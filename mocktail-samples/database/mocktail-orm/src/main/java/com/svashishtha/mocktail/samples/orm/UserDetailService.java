package com.svashishtha.mocktail.samples.orm;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class UserDetailService{

	private EntityManagerFactory emf;

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public UserDetailService() {
	}
	
	public UserDetail getUserDetail(Long userId) {
		EntityManager newEm = emf.createEntityManager();
		EntityTransaction newTx = newEm.getTransaction();
		newTx.begin();

		UserDetail userDetail = newEm.find(UserDetail.class, userId);
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

	public List<UserDetail> getAllUsers() {
		EntityManager newEm = emf.createEntityManager();
		
		return newEm.createQuery("from UserDetail").getResultList();
	}

	
}
