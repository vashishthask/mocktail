package org.mocktail;

import org.junit.Before;
import org.junit.Test;

public class UserDetailServiceTest {

	private UserDetailService userDetailService;

	@Before
	public void setup() {
		userDetailService = new UserDetailService();
	}

	@Test
	public void shouldSaveUserDetail() {
		userDetailService.saveUserDetail(new UserDetail("user"));
	}

}
