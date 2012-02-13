package org.mocktail.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.mocktail.MocktailContainer;

public class UniqueIdGeneratorTest {

	UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getUniqueIdGenerator();

	@SuppressWarnings("unchecked")
	@Test
	public void shouldGenerateUniqueIdUsingHashCode() throws Exception {
		int uniqueId = uniqueIdGenerator.getUniqueId("sandy", "ganesh", 12,
				23.0);
		assertThat(uniqueId, is(1332060422));

		uniqueId = uniqueIdGenerator.getUniqueId("ganesh", "sandy", 12, 23.0);
		assertThat(uniqueId, is(555387972));

		uniqueId = uniqueIdGenerator.getUniqueId(Arrays.asList("sandy",
				"ganesh", 12, 23.0));
		assertThat(uniqueId, is(1332060453));
		
		Object [] array = {"ganesh", 12, 23.0};
		Object [] array2 = {"ganesh", 12, 23.0};
		assertThat(uniqueIdGenerator.getUniqueId("sandy", array), is(uniqueIdGenerator.getUniqueId("sandy", array2)));
	}

}
