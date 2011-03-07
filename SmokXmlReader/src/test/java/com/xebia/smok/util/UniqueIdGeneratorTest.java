package com.xebia.smok.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;


public class UniqueIdGeneratorTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldGenerateUniqueIdUsingHashCode() throws Exception {
		int uniqueId = UniqueIdGenerator.HASH_CODE_IMPL.getUniqueId("sandy", "ganesh", 12, 23.0);
		assertThat(uniqueId, is(1332060422));
		
		uniqueId = UniqueIdGenerator.HASH_CODE_IMPL.getUniqueId("ganesh", "sandy", 12, 23.0);
		assertThat(uniqueId, is(555387972));

		uniqueId = UniqueIdGenerator.HASH_CODE_IMPL.getUniqueId(Arrays.asList("sandy", "ganesh", 12, 23.0));
		assertThat(uniqueId, is(1332060453));
	}

}
